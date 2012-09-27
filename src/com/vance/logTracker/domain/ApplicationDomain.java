package com.vance.logTracker.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.vance.logTracker.persistence.factory.MongodbInstanceFactory;

public class ApplicationDomain extends BaseAPIDomain {


	private ArrayList<CBSAccessLog> accessLogResults = null;

	private static DBCollection dbColl =initCollection();

	public static DBCollection initCollection() {
		String dbName = "AccessLogs";
		String url = "10.201.10.210";
		String collectionName = "AM_ACCESS_LOGS";
		MongodbInstanceFactory.connect(url);
		DB db = MongodbInstanceFactory.getDBInstance(dbName);
		DBCollection dbColl = db.getCollection(collectionName);
		return dbColl;
	}

	// public void queryObject(List<String> apiNameList) {
	// ArrayList<CBSAccessLog> accessLogResults = new ArrayList<CBSAccessLog>();
	//
	// String m =
	// "function Map() {	emit(this.action.split(' ')[1], {'count': 1,'sumRes': 0,'res': parseInt(this.responseTime),'count_3000': 0,'count_2000': 0,'count_1000': 0,'count_500': 0,'count_200': 0,'count_100': 0,'count_50': 0,'count_0': 0,'maxRes': 0,'minRes': 0,'avgRes': 0,'dataSize': this.dataSize	});}";
	// String r = "function Reduce(action, combinValues) {    var res = 0;  "
	// + "  var totalHits = 0;    var maxRes = 0;    var minRes = 0;  "
	// + "  var count_3000 = 0;    var count_1000 = 0;    var count_2000 = 0;  "
	// +
	// "  var count_500 = 0;	var count_200=0;	var count_50=0;	var count_0=0;  "
	// + "  combinValues.forEach(function (v) {       "
	// + " print(v.res);		"
	// + "	print(v.dataSize);      "
	// + "  res += v.res;   "
	// + "     totalHits += v.count; 	   "
	// + "     if (v.res > 3000) count_3000 += 1;   "
	// + "     if (v.res > 2000 && v.res <= 3000) count_2000 += 1;    "
	// + "    if (v.res > 1000 && v.res <= 2000) count_1000 += 1;     "
	// + "   if (v.res > 500 && v.res <= 1000) count_500 += 1;	"
	// +
	// "	if(v.res>200&&v.res<=500) count_200+=1;		if(v.res>50&&v.res<=200) count_50+=1;	"
	// +
	// "	if(v.res>0&&v.res<=50) count_0+=1;        if (maxRes == 0 || maxRes < v.res) maxRes = v.res;      "
	// +
	// "  if (minRes == 0 || minRes > v.res) minRes = v.res;        dataSize = v.dataSize;    }); "
	// +
	// "  var aggResult = {        'count': 0,        'sumRes': 0,        'res': 0,        'count_3000': 0,      "
	// +
	// "  'count_2000': 0,        'count_1000': 0,        'count_500': 0,	" +
	// "	'count_200':0,		'count_50':0,		'count_0':0,        'maxRes': 0,     " +
	// "   'minRes': 0,        'avgRes': 0,        'dataSize': 0    };   " +
	// " aggResult.count = totalHits;    " +
	// "aggResult.sumRes = res;   " +
	// " aggResult.count_3000 = count_3000;  " +
	// "  aggResult.count_2000 = count_2000;   " +
	// " aggResult.count_500 = count_500;	" +
	// "aggResult.count_200 = count_200;	" +
	// "aggResult.count_50 = count_50;	" +
	// "aggResult.count_0 = count_0;  " +
	// "  aggResult.maxRes = maxRes;   " +
	// " aggResult.minRes = minRes;  " +
	// "  aggResult.avgRes = aggResult.sumRes / aggResult.count; " +
	// "   aggResult.dataSize = dataSize;    return (action, aggResult)}";
	// String f = "function Finalize(action, aggResult) {   " +
	// " return {        'api': action,        'totalHits': aggResult.count,  "
	// +
	// "      'minResponseTime(ms)': aggResult.minRes,        'maxResponseTime(ms)': aggResult.maxRes,    "
	// +
	// "    'avgResponseTime(ms)': aggResult.avgRes,        'avgResponseDataSize': aggResult.dataSize / aggResult.count,        'count_3000': aggResult.count_3000,        'count_1000': aggResult.count_1000,		'count_2000':aggResult.count_2000,		'count_500':aggResult.count_500,		'count_200':aggResult.count_200,		'count_50':aggResult.count_50,		'count_0':aggResult.count_0    }};";
	// System.out.println(m);
	// System.out.println(r);
	//
	// System.out.println(f);
	//
	//
	// if (apiNameList != null) {
	// for (String apiName : apiNameList) {
	//
	// BasicDBObject query = new BasicDBObject();
	//
	// query.put("action", Pattern.compile("(.*)" + apiName + "(.*)",
	// Pattern.CASE_INSENSITIVE));
	// // query.put("action",new BasicDBObject("$all",null));
	//
	// MapReduceCommand cmd = new MapReduceCommand(dbColl, m, r, null,
	// MapReduceCommand.OutputType.INLINE, query);
	// cmd.setFinalize(f);
	// MapReduceOutput out = dbColl.mapReduce(cmd);
	//
	// accessLogResults.add(mapReduceMapping(out.results()));
	// }
	// } else if (apiNameList == null) {
	// Collection<? extends String> apiTestSamples = TestSamples.get();
	// }
	//
	// if (this.accessLogResults == null) {
	// this.accessLogResults = new ArrayList<CBSAccessLog>();
	// this.accessLogResults.addAll(accessLogResults);
	// }
	//
	// }

	protected CBSAccessLog mapping(MapReduceCommand cmd, DBCollection dbColl,
			int size, int limit,Date date) {
		ArrayList<CBSAccessLog> cbsAccessLogs = new ArrayList<CBSAccessLog>();
		int loopCount = size % limit;
		for (int i = limit; i <= size; i = i + limit) {
			cmd.setLimit(i);
			MapReduceOutput mapReduceOutput = dbColl.mapReduce(cmd);
			System.out.println(mapReduceOutput.results());
			CBSAccessLog cbsAccessLog = mapReduceMapping(mapReduceOutput.results(),limit);
			cbsAccessLogs.add(cbsAccessLog);
		}

		cmd.setLimit(loopCount);
		MapReduceOutput mapReduceOutput = dbColl.mapReduce(cmd);
		CBSAccessLog cbsAccessLog = mapReduceMapping(mapReduceOutput.results(),loopCount);
		System.out.println(mapReduceOutput.results());
		cbsAccessLogs.add(cbsAccessLog);
		
		CBSAccessLog aggregationedAccessLog=cacheProcess(cbsAccessLogs,size,date);
//		aggregationedAccessLog.setTotalHits(Double.valueOf(size));
//		aggregationedAccessLog.setNinetyPercentResp(generate90Line(aggregationedAccessLog.getTotalHits(),aggregationedAccessLog.getApi()));
//		aggregationedAccessLog.setAvgResponseTime(aggregationedAccessLog.getTotalResponseTime()/size);
//		aggregationedAccessLog.setAvgResponseDataSize(aggregationedAccessLog.getResponseDataSize()/size);
		return aggregationedAccessLog ;
	}

	private CBSAccessLog cacheProcess(ArrayList<CBSAccessLog> cbsAccessLogs,int logsCount,Date date) {
		CBSAccessLog result = new CBSAccessLog();
		double totalHits = Double.valueOf(logsCount);
		double responseDataSize = 0.00;
		double minResponseTime = 0.00;
		double totalResponseTime = 0.00;
		double maxResponseTime = 0.00;
		double count_3000 = 0;
		double count_2000 = 0;
		double count_1000 = 0;
		double count_500 = 0;
		double count_200 = 0;
		double count_50 = 0;
		double count_0 = 0;
		String url="";
		String api="";
		for (CBSAccessLog cbsAccessLog : cbsAccessLogs) {
			System.out.println(cbsAccessLog.getUrl());
			url=cbsAccessLog.getUrl();
			if (cbsAccessLog.getMaxResponseTime() >= maxResponseTime) {
				maxResponseTime = cbsAccessLog.getMaxResponseTime();
			}
			if (cbsAccessLog.getMinResponseTime() <= minResponseTime ||minResponseTime==0.00) {
				minResponseTime = cbsAccessLog.getMinResponseTime();
			}
			responseDataSize += cbsAccessLog.getResponseDataSize();
			totalResponseTime += cbsAccessLog.getTotalResponseTime();
//			totalHits += cbsAccessLog.getTotalHits();
			count_3000 += Double.valueOf(cbsAccessLog.getCount3000());
			count_2000 += Double.valueOf(cbsAccessLog.getCount_2000());
			count_1000 += Double.valueOf(cbsAccessLog.getCount_1000());
			count_500 += Double.valueOf(cbsAccessLog.getCount_500());
			count_200 += Double.valueOf(cbsAccessLog.getCount_200());
			count_50 += Double.valueOf(cbsAccessLog.getCount_50());
			count_0 += Double.valueOf(cbsAccessLog.getCount_0());
		}
		result.setDate(date);
		result.setApi(api);
		result.setTotalHits(totalHits);
		result.setAvgResponseTime(totalResponseTime / totalHits);
		result.setMinResponseTime(minResponseTime);
		result.setMaxResponseTime(maxResponseTime);
		result.setAvgResponseDataSize(responseDataSize / totalHits);
		result.setCount_3000(String.valueOf(count_3000));
		result.setCount_2000(String.valueOf(count_2000));
		result.setCount_1000(String.valueOf(count_1000));
		result.setCount_500(String.valueOf(count_500));
		result.setCount_200(String.valueOf(count_200));
		result.setCount_50(String.valueOf(count_50));
		result.setCount_0(String.valueOf(count_0));
		result.setTotalHits(totalHits);
		result.setUrl(url);
		result.setNinetyPercentResp(generate90Line(result.getTotalHits(), result.getUrl()));
		
		return result;

	}

	protected CBSAccessLog mapReduceMapping(Iterable<DBObject> results,int totalCount) {
		CBSAccessLog cbsAccessLog = new CBSAccessLog();

		for (DBObject obj : results) {
			Set<String> key1 = obj.keySet();
			for (String s : key1) {
				System.out.println(s);
				if (obj.get(s)!= null) {
					if (obj.get(s).getClass().getName() == "com.mongodb.CommandResult") {
						CommandResult commandResult = (CommandResult) obj
								.get(s);
						Set<String> key2 = commandResult.keySet();
						for (String s2 : key2) {
							if (s2.contains("api")) {
								
								cbsAccessLog.setApi(commandResult.get(s2)==null?"unknowApi":commandResult.get(s2).toString());
								
							}
							if(totalCount>0){
								cbsAccessLog.setTotalHits(Double.valueOf(totalCount));
							}else{
								if (s2.contains("totalHits")) {
									cbsAccessLog.setTotalHits(Double
											.valueOf(commandResult.get(s2)
													.toString()));
								}
								
							}
							if (s2.contains("minResponseTime")) {
								// if (commandResult.get(s2) != null) {
								cbsAccessLog.setMinResponseTime(Double
										.valueOf(commandResult.get(s2)
												.toString()));
								// } else {
								// cbsAccessLog.setMinResponseTime();
								// }
							}
							if (s2.contains("maxResponseTime")) {
								cbsAccessLog.setMaxResponseTime(Double
										.valueOf(commandResult.get(s2)
												.toString()));
							}

							if (s2.contains("avgResponseTime")) {
								cbsAccessLog.setAvgResponseTime(Double
										.valueOf(commandResult.get(s2)
												.toString()));
							}
							if (s2.contains("count_3000")) {
								cbsAccessLog.setCount_3000(commandResult
										.get(s2).toString());
							}
							if (s2.contains("avgResponseDataSize")) {
								cbsAccessLog.setAvgResponseDataSize(Double
										.valueOf(commandResult.get(s2)
												.toString()));
							}

							if (s2.contains("count_2000")) {
								cbsAccessLog.setCount_2000(commandResult
										.get(s2).toString());
							}

							if (s2.contains("count_1000")) {
								cbsAccessLog.setCount_1000(commandResult
										.get(s2).toString());
							}
							if (s2.contains("count_500")) {
								cbsAccessLog.setCount_500(commandResult.get(s2)
										.toString());
							}
							if (s2.contains("count_200")) {
								cbsAccessLog.setCount_200(commandResult.get(s2)
										.toString());
							}
							if (s2.contains("count_50")) {
								cbsAccessLog.setCount_50(commandResult.get(s2)
										.toString());
							}
							if (s2.contains("count_0")) {
								cbsAccessLog.setCount_0(commandResult.get(s2)
										.toString());
							}
							if (s2.contains("date")) {
								SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:ss:mm");
								
								try {
									cbsAccessLog.setDate(sdf.parse(commandResult.get(s2).toString()));
								} catch (ParseException e) {
									System.out.println("error occurs ");
									e.printStackTrace();
								}
							}
							if (s2.contains("totalResponseDataSize")) {
								cbsAccessLog.setResponseDataSize(commandResult
										.getDouble(s2));
							}
							if(s2.contains("totalResponseTime")){
								cbsAccessLog.setTotalResponseTime(commandResult.getDouble(s2));
							}
							
							if(s2.contains("url")){
								cbsAccessLog.setUrl(commandResult.get(s2).toString());
							}
							
						}
					}
				}
			}
		}

		// cbsAccessLog.setNinetyPercentResp(generate90Line(cbsAccessLog.getTotalHits(),
		// cbsAccessLog.getApi()));

		return cbsAccessLog;

	}

	private int generate90Line(Double totalHits, String url) {
		if (totalHits != null && totalHits > 10) {
			// if(this.dbColl==null){
			//
			// }
//			url=url.replace("/","//");
			DBObject query = new BasicDBObject();
			query.put("url",
					java.util.regex.Pattern.compile("" + url + ""));
			int numToLimit = (int) (totalHits * 0.9);
			int numToSkip = (int) (totalHits - numToLimit);
			int batchSize = 1;
			DBCursor dbCur = dbColl
					.find(query)
					.sort(new BasicDBObject("responseTime", -1))
					.limit(numToLimit).skip(numToSkip);
//			Iterator<DBObject> it = dbCur.iterator();
			
//			
			
//			System.out.println("90%line is: "+Integer.valueOf(dbCur.next().get("responseTime").toString()));
			
			while(dbCur.hasNext()){
//				System.out.println(dbCur.next());
//			
			int ninetypercentres=Integer.valueOf(dbCur.next().get("responseTime").toString())==null?-1:Integer.valueOf(dbCur.next().get("responseTime").toString());
			if(ninetypercentres==-1){
				return -1;
			}
			return ninetypercentres;
			}
			return -1;
		} else {
			return -1;
		}
	}

	// public ArrayList<CBSAccessLog> getAccessLogResults() {
	// if (this.accessLogResults == null) {
	// Collection<? extends String> apiTestSamples = TestSamples.get();
	// ArrayList<String> apiLists = new ArrayList<String>();
	// apiLists.addAll(apiTestSamples);
	// queryObject(apiLists);
	// }
	// return accessLogResults;
	//
	// }

	private static Iterable<DBObject> Iterable(Object object) {
		return null;
	}

	public void queryObject(String apiName) {
		// TODO Auto-generated method stub

	}
}
