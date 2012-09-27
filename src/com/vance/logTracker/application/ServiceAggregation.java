package com.vance.logTracker.application;

import java.net.UnknownHostException;
import java.sql.Savepoint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.QueryBuilder;
import com.vance.logTracker.domain.BaseAPIDomain;
import com.vance.logTracker.domain.CBSAccessLog;
import com.vance.logTracker.domain.CBSServiceMRDomain;
import com.vance.logTracker.persistence.tasks.CBSServiceAggreagationSave;
import com.vance.logTracker.util.DateByWeek;
import com.vance.logTracker.util.DatebyDay;

public class ServiceAggregation extends CBSServiceMRDomain {

	private String map = null;

	private String reduce = null;

	private String finalize = null;

	private final int limit = 1000;

	public static ArrayList<CBSAccessLog> accessLogResults = null;

	//
	// @Override
	// public void setMap() {
	// String m =
	// "function Map(){var myDate=this.date;var month=myDate.getMonth()+1;var year=myDate.getFullYear();emit(this.action,"
	// +
	// "{'count':1,'sumRes':0,'res':parseInt(this.responseTime),'count_3000':0,'count_2000':0,'count_1000':0,'count_500':0,'count_200':0,'count_100':0,"
	// +
	// "'count_50':0,'count_0':0,'maxRes':0,'minRes':0,'avgRes':0,'dataSize':this.dataSize})}";
	// this.map=m;
	// }
	//
	//
	// @Override
	// public void setReduce() {
	// String r =
	// "function Reduce(action,combinValues){var res=0;var totalHits=0;" +
	// "var maxRes=0;var minRes=0;var count_3000=0;var count_1000=0;var count_2000=0;var count_500=0;var count_200=0;"
	// +
	// "var count_50=0;var count_0=0;var date='';var dataSize=0;combinValues.forEach(function(v){res+=v.res;totalHits+=v.count;"
	// +
	// "if(v.res>3000)count_3000+=1;if(v.res>2000&&v.res<=3000)count_2000+=1;if(v.res>1000&&v.res<=2000)count_1000+=1;if(v.res>500&&v.res<=1000)count_500+=1;"
	// +
	// "if(v.res>200&&v.res<=500)count_200+=1;if(v.res>50&&v.res<=200)count_50+=1;if(v.res>0&&v.res<=50)count_0+=1;"
	// +
	// "if(maxRes==0||maxRes<v.res)maxRes=v.res;if(minRes==0||minRes>v.res)minRes=v.res;dataSize+=v.dataSize});var aggResult={'count':0,'sumRes':0,'res':0,'count_3000':0,'count_2000':0,'count_1000':0,'count_500':0,'count_200':0,'count_50':0,'count_0':0,'maxRes':0,'minRes':0,'avgRes':0,'dataSize':0};aggResult.count=totalHits;aggResult.sumRes=res;aggResult.count_3000=count_3000;aggResult.count_2000=count_2000;aggResult.count_1000=count_1000;aggResult.count_500=count_500;aggResult.count_200=count_200;aggResult.count_50=count_50;"
	// +
	// "aggResult.count_0=count_0;aggResult.maxRes=maxRes;aggResult.minRes=minRes;aggResult.dataSize=dataSize;return(action,aggResult)}";
	// this.reduce=r;
	// }
	//
	// @Override
	// public void setFinilize() {
	// String f =
	// "function Finalize(action,aggResult){return{'api':action,'totalHits':aggResult.count,'minResponseTime(ms)':aggResult.minRes,'maxResponseTime(ms)':aggResult.maxRes,'totalResponseTime(ms)':aggResult.sumRes,'totalResponseDataSize':aggResult.dataSize,'count_3000':aggResult.count_3000,'count_1000':aggResult.count_1000,'count_2000':aggResult.count_2000,'count_500':aggResult.count_500,'count_200':aggResult.count_200,'count_50':aggResult.count_50,'count_0':aggResult.count_0}};";
	// this.finalize=f;
	//
	// }

	@SuppressWarnings("deprecation")
	public synchronized ArrayList process(List<String> apiLists,
			String serviceName,String dbCollectName) {
		// setMap();
		// setReduce();
		// setFinilize();
		ArrayList<CBSAccessLog> accessLogResults = new ArrayList<CBSAccessLog>();
		Mongo mongo;
		DBCollection dbColl;
		QueryBuilder queryBuilder = new QueryBuilder();
		SimpleDateFormat origDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		SimpleDateFormat finalDateFormat=new SimpleDateFormat("yyyy-MMM-dd");
//		String[] dbCollectionNames = { "CBS_ACCESS_LOGS_20111107","CBS_ACCESS_LOGS_20111108",
//				"CBS_ACCESS_LOGS_20111109","CBS_ACCESS_LOGS_20111110","CBS_ACCESS_LOGS_20111111"};

//		String[] dbCollectionNames = { "CBS_ACCESS_LOGS_20110824","CBS_ACCESS_LOGS_20110907"
//				,"CBS_ACCESS_LOGS_20111028","CBS_ACCESS_LOGS_20111030","CBS_ACCESS_LOGS_20111101","CBS_ACCESS_LOGS_20111103"};
			try {
				mongo = new Mongo("10.201.10.210");
				System.out.println("collectName is" +dbCollectName);
				dbColl=mongo.getDB("CBSNov").getCollection(dbCollectName.toString());
				List<DBObject> collIndexList=dbColl.getIndexInfo();
				boolean createResponseTimeIndex=false;
				boolean createActionIndex=false;
				
				for(DBObject collIndex:collIndexList){
					System.out.println(dbColl.getName()+" index info: "+collIndex.get("name"));
					if(collIndex.get("name").toString().contains("responseTime_1_action_1_service_1")){
						createResponseTimeIndex=true;
					}
					if(collIndex.get("name").toString().contains("action_1_responseTime_1_service_1")){
						
						createActionIndex=true;
					}
					
				}
				createDBColleIndex(dbColl,createResponseTimeIndex,createActionIndex);
				
				for (String apiName : apiLists) {

						BasicDBObject query = new BasicDBObject();

//						Date startDate = simpleDateFormate.parse(dateByWeek
//								.getWeekStartDate());
//
//						Date endDate = simpleDateFormate.parse(dateByWeek
//								.getWeekEndDate());

//						queryBuilder.put("date");

//						queryBuilder.greaterThan(startDate);

//						queryBuilder.lessThanEquals(endDate);

						query.put("action", apiName);

//						query.put("url", serviceName);
						

//						query.putAll(queryBuilder.get());

						// MapReduceCommand cmd = new MapReduceCommand(dbColl,
						// map, reduce, null,
						// MapReduceCommand.OutputType.INLINE, query);

						// cmd.setFinalize(finalize);
						// cmd.setLimit(limit);
						Date finalDate=new Date();
						int size = dbColl.find(query).size();
							
						
						//System.out.println(apiName + " total size is: " + size);
						
						if (size > 0) {
							
							if(dbColl.findOne(query).containsField("date")){
								Date date=origDateFormat.parse(dbColl.findOne(query).get("date").toString());
//								finalDate.setYear(date.getYear());
//								finalDate.setMonth(date.getMonth());
								finalDate.setDate(date.getDate());
							}

							CBSAccessLog tempResult = mapping(dbColl, apiName,
									serviceName, query, size);
							tempResult.setDate(finalDate);
							tempResult.setLogDateFlag(dbCollectName.toString());
							if (serviceName != null) {
								tempResult.setService(serviceName);
							}
							accessLogResults.add(tempResult);
						}else{
							System.out.println("Can't find the data: "+apiName+"on "+dbCollectName);
							CBSAccessLog tempResult=new CBSAccessLog();
							double dummyDouble=-1.00;
							String dummString="-1";
							tempResult.setApi(apiName);
							tempResult.setAvgResponseDataSize(dummyDouble);
							tempResult.setAvgResponseTime(dummyDouble);
							tempResult.setCount_0(dummString);
							tempResult.setCount_1000(dummString);
							tempResult.setCount_200(dummString);
							tempResult.setCount_2000(dummString);
							tempResult.setCount_3000(dummString);
							tempResult.setCount_50(dummString);
							tempResult.setCount_500(dummString);
							tempResult.setDate(null);
							tempResult.setLogDateFlag(dbCollectName.toString());
							tempResult.setMaxResponseTime(dummyDouble);
							tempResult.setMinResponseTime(dummyDouble);
							tempResult.setNinetyPercentResp(dummyDouble);
							tempResult.setService(serviceName);
							tempResult.setTotalHits(dummyDouble);
							tempResult.setTotalResponseTime(dummyDouble);
							tempResult.setUrl(dummString);
							accessLogResults.add(tempResult);
						}
					}
				this.setAccessLogResults(accessLogResults);
				CBSServiceAggreagationSave task = new CBSServiceAggreagationSave();
				task.insert(accessLogResults);
				task.commit();
				return accessLogResults;
			}
			 catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (MongoException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return null;
	}

	private void createDBColleIndex(DBCollection dbColl,
			boolean createResponseTimeIndex, boolean createActionIndex) {
		if(!createResponseTimeIndex){
			DBObject objIndex_res_apiName_service=new BasicDBObjectBuilder().add("responseTime",1).add("action", 1).add("service",1).get();
			dbColl.createIndex(objIndex_res_apiName_service);
		}
		if(!createActionIndex){
//			DBObject objIndex_action_date_service=new BasicDBObjectBuilder().add("action",1).add("date", 1).add("service",1).get();
			DBObject objIndex_action_service=new BasicDBObjectBuilder().add("action",1).add("responseTime",1).add("service",1).get();
			
			dbColl.createIndex(objIndex_action_service);
			
		}
	}

	//

	private void setAccessLogResults(ArrayList<CBSAccessLog> accessLogResults) {

		this.accessLogResults = accessLogResults;
	}

}
