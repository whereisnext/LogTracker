package com.vance.logTracker.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.regex.Pattern;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.QueryBuilder;
import com.vance.logTracker.persistence.factory.MongodbInstanceFactory;
import com.vance.logTracker.util.TestSamples;

public class CBSApiDomain extends BaseAPIDomain {

	private ArrayList<CBSAccessLog> accessLogResults = null;

	private DBCollection dbColl = initCollection();

	public CBSApiDomain() {
		super();
	}

	@Override
	public void queryObject(String apiName) {
		ArrayList<CBSAccessLog> accessLogResults = new ArrayList<CBSAccessLog>();
		String dbName = "AccessLogs";
		String url = "10.201.10.210";
//		MongodbInstanceFactory.connect(url);
//		DB db = MongodbInstanceFactory.getDBInstance(dbName);
		String collectionName = "CBS_ACCESS_LOGS";
//		dbColl = db.getCollection(collectionName);
		String m = "function Map(){emit(this.action.split(' ')[1],{'count':1,'sumRes':0,'res':"
				+ "parseInt(this.responseTime),'count_3000':0,'count_2000':0,'count_1000':0,'count_500':0,'count_200':0,'count_100':0,'count_50':0,'count_0':0,'maxRes':0,'minRes':0,'avgRes':0,'dataSize':this.dataSize,'date':this.date})}";
		String r = "function Reduce(action, combinValues) {    var res = 0;  "
				+ "  var totalHits = 0;    var maxRes = 0;    var minRes = 0;  "
				+ "  var count_3000 = 0;    var count_1000 = 0;    var count_2000 = 0;  "
				+ "  var count_500 = 0;	var count_200=0;	var count_50=0;	var count_0=0;  "
				+ "  combinValues.forEach(function (v) {       "
				+ " print(v.res);		"
				+ "	print(v.dataSize);      "
				+ "  res += v.res;   "
				+ "     totalHits += v.count; 	   "
				+ "     if (v.res > 3000) count_3000 += 1;   "
				+ "     if (v.res > 2000 && v.res <= 3000) count_2000 += 1;    "
				+ "    if (v.res > 1000 && v.res <= 2000) count_1000 += 1;     "
				+ "   if (v.res > 500 && v.res <= 1000) count_500 += 1;	"
				+ "	if(v.res>200&&v.res<=500) count_200+=1;		if(v.res>50&&v.res<=200) count_50+=1;	"
				+ "	if(v.res>0&&v.res<=50) count_0+=1;        if (maxRes == 0 || maxRes < v.res) maxRes = v.res;      "
				+ "  if (minRes == 0 || minRes > v.res) minRes = v.res;        dataSize = v.dataSize;    }); "
				+ "  var aggResult = {        'count': 0,        'sumRes': 0,        'res': 0,        'count_3000': 0,      "
				+ "  'count_2000': 0,        'count_1000': 0,        'count_500': 0,	"
				+ "	'count_200':0,		'count_50':0,		'count_0':0,        'maxRes': 0,     "
				+ "   'minRes': 0,        'avgRes': 0,        'dataSize': 0    };   "
				+ " aggResult.count = totalHits;    "
				+ "aggResult.sumRes = res;   "
				+ " aggResult.count_3000 = count_3000;  "
				+ "  aggResult.count_2000 = count_2000;   "
				+ " aggResult.count_500 = count_500;	"
				+ "aggResult.count_200 = count_200;	"
				+ "aggResult.count_50 = count_50;	"
				+ "aggResult.count_0 = count_0;  "
				+ "  aggResult.maxRes = maxRes;   "
				+ " aggResult.minRes = minRes;  "
				+ "  aggResult.avgRes = aggResult.sumRes / aggResult.count; "
				+ "   aggResult.dataSize = dataSize;    return (action, aggResult)}";
		String f = "function Finalize(action, aggResult) {   "
				+ " return {        'api': action,        'totalHits': aggResult.count,  "
				+ "      'minResponseTime(ms)': aggResult.minRes,        'maxResponseTime(ms)': aggResult.maxRes,    "
				+ "    'avgResponseTime(ms)': aggResult.avgRes,        'avgResponseDataSize': aggResult.dataSize / aggResult.count, " +
				"'count_3000': aggResult.count_3000,        'count_1000': aggResult.count_1000,		'count_2000':aggResult.count_2000,	" +
				"	'count_500':aggResult.count_500,		'count_200':aggResult.count_200,		'count_50':aggResult.count_50,		" +
				"'count_0':aggResult.count_0    }};";
		System.out.println(m);
		System.out.println(r);

		System.out.println(f);
		if (apiName != null) {
			for (int myDay=29;myDay<=30;myDay++) {

			BasicDBObject query = new BasicDBObject();

			query.put("action", Pattern.compile("(.*)" + apiName + "(.*)",
					Pattern.CASE_INSENSITIVE));

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:ss:mm", Locale.CHINA);
			QueryBuilder queryBuilder = new QueryBuilder();

			queryBuilder.put("date");
			try {
				queryBuilder.greaterThanEquals(simpleDateFormat
						.parse("2011-10-"+myDay+" 01:00:00"));
				queryBuilder.lessThanEquals(simpleDateFormat
						.parse("2011-10-"+(myDay+1)+" 03:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			query.putAll(queryBuilder.get());
			MapReduceCommand cmd = new MapReduceCommand(dbColl, m, r, null,
					MapReduceCommand.OutputType.REPLACE, query);
			cmd.setFinalize(f);
			MapReduceOutput out = dbColl.mapReduce(cmd);
			accessLogResults.add(mapReduceMapping(out.results(),-1));
			}
		} else if (apiName == null) {
			Collection<? extends String> apiTestSamples = TestSamples.get();
		}

		if (this.getAccessLogResults() == null) {
			this.setAccessLogResults(new ArrayList<CBSAccessLog>());
			this.getAccessLogResults().addAll(accessLogResults);
		}

	}

	public ArrayList<CBSAccessLog> getAccessLogResults() {
		return accessLogResults;
	}

	public void setAccessLogResults(ArrayList<CBSAccessLog> accessLogResults) {
		this.accessLogResults = accessLogResults;
	}

	

}
