package com.vance.logTracker.application;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.QueryBuilder;
import com.vance.logTracker.domain.BaseAPIDomain;
import com.vance.logTracker.domain.CBSAccessLog;
import com.vance.logTracker.persistence.tasks.CBSApiAggreagationSave;

public class APIAggregation extends BaseAPIDomain implements MapReduce {

	private String map = null;

	private String reduce = null;

	private String finalize = null;

	private int limit = 1000;

	public static ArrayList<CBSAccessLog> apiLevelResults = null;

	@Override
	public void setMap() {
		String m = "function Map(){var myDate=this.date;var month=myDate.getMonth()+1;var year=myDate.getFullYear();emit(this.action," +
				"{'count':1,'sumRes':0,'res':parseInt(this.responseTime),'count_3000':0,'count_2000':0,'count_1000':0,'count_500':0,'count_200':0,'count_100':0," +
				"'count_50':0,'count_0':0,'maxRes':0,'minRes':0,'avgRes':0,'dataSize':this.dataSize})}";
		this.map=m;
	}

	
	@Override
	public void setReduce() {
		String r = "function Reduce(action,combinValues){var res=0;var totalHits=0;" +
				"var maxRes=0;var minRes=0;var count_3000=0;var count_1000=0;var count_2000=0;var count_500=0;var count_200=0;" +
				"var count_50=0;var count_0=0;var date='';var dataSize=0;combinValues.forEach(function(v){res+=v.res;totalHits+=v.count;" +
				"if(v.res>3000)count_3000+=1;if(v.res>2000&&v.res<=3000)count_2000+=1;if(v.res>1000&&v.res<=2000)count_1000+=1;if(v.res>500&&v.res<=1000)count_500+=1;" +
				"if(v.res>200&&v.res<=500)count_200+=1;if(v.res>50&&v.res<=200)count_50+=1;if(v.res>0&&v.res<=50)count_0+=1;" +
				"if(maxRes==0||maxRes<v.res)maxRes=v.res;if(minRes==0||minRes>v.res)minRes=v.res;dataSize+=v.dataSize});var aggResult={'count':0,'sumRes':0,'res':0,'count_3000':0,'count_2000':0,'count_1000':0,'count_500':0,'count_200':0,'count_50':0,'count_0':0,'maxRes':0,'minRes':0,'avgRes':0,'dataSize':0};aggResult.count=totalHits;aggResult.sumRes=res;aggResult.count_3000=count_3000;aggResult.count_2000=count_2000;aggResult.count_1000=count_1000;aggResult.count_500=count_500;aggResult.count_200=count_200;aggResult.count_50=count_50;" +
				"aggResult.count_0=count_0;aggResult.maxRes=maxRes;aggResult.minRes=minRes;aggResult.dataSize=dataSize;return(action,aggResult)}";
		this.reduce=r;
	}

	@Override
	public void setFinilize() {
		String f = "function Finalize(action,aggResult){return{'api':action,'totalHits':aggResult.count,'minResponseTime(ms)':aggResult.minRes,'maxResponseTime(ms)':aggResult.maxRes,'totalResponseTime(ms)':aggResult.sumRes,'totalResponseDataSize':aggResult.dataSize,'count_3000':aggResult.count_3000,'count_1000':aggResult.count_1000,'count_2000':aggResult.count_2000,'count_500':aggResult.count_500,'count_200':aggResult.count_200,'count_50':aggResult.count_50,'count_0':aggResult.count_0}};";
		this.finalize=f;
		
	}

	@Override
	public synchronized ArrayList process(List<String> apiLists,String name) {

		setMap();
		setReduce();
		setFinilize();
		ArrayList<CBSAccessLog> accessLogResults = new ArrayList<CBSAccessLog>();
		Mongo mongo;
		try {
			mongo = new Mongo("10.201.10.210");
			DBCollection dbColl = mongo.getDB("AccessLogs").getCollection(
					"CBS_ACCESS_LOGS");

			for (String apiName : apiLists) {

				BasicDBObject query = new BasicDBObject();

				query.put("action", Pattern.compile("(.*)" + apiName + "(.*)",
						Pattern.CASE_INSENSITIVE));
				for (int myDay = 29; myDay <= 31; myDay++) {
					// query.put("action",new BasicDBObject("$all",null));

					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd", Locale.CHINA);
					QueryBuilder queryBuilder = new QueryBuilder();

					queryBuilder.put("date");
					try {
						queryBuilder.greaterThanEquals(simpleDateFormat
								.parse("2011-10-" + myDay));
						queryBuilder.lessThan(simpleDateFormat
								.parse("2011-10-" + (myDay + 1)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					query.putAll(queryBuilder.get());

					int size = dbColl.find(query).size();
					if (size > 0) {
						MapReduceCommand cmd = new MapReduceCommand(dbColl,
								map, reduce, null,
								MapReduceCommand.OutputType.INLINE, query);
						cmd.setFinalize(finalize);
						// MapReduceOutput out = dbColl.mapReduce(cmd);
						CBSAccessLog result=mapping(cmd, dbColl, size, limit);
						result.setDate(simpleDateFormat.parse("2011-10-" + myDay));
						accessLogResults.add(result);
					}
				}
			}
			this.setAccessLogResults(accessLogResults);
			
			CBSApiAggreagationSave cbsAPISave=new CBSApiAggreagationSave();
			cbsAPISave.insert(apiLevelResults);
			return accessLogResults;

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	private void setAccessLogResults(ArrayList<CBSAccessLog> apiLevelResults) {

		this.apiLevelResults = apiLevelResults;
	}


	@Override
	public Double getMapReduceResult(DBCollection dbColl, String apiName,
			String serviceName, DBObject query) {
		// TODO Auto-generated method stub
		return null;
	}

}
