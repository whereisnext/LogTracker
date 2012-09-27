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
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.QueryBuilder;
import com.vance.logTracker.domain.ApplicationMRDomain;
import com.vance.logTracker.domain.BaseAPIDomain;
import com.vance.logTracker.domain.CBSAccessLog;
import com.vance.logTracker.domain.CBSServiceMRDomain;
import com.vance.logTracker.persistence.tasks.ApplicationAggreagationSave;
import com.vance.logTracker.persistence.tasks.CBSServiceAggreagationSave;
import com.vance.logTracker.util.DateByWeek;
import com.vance.logTracker.util.DatebyDay;

public class ApplicationAggregation extends ApplicationMRDomain {

	private String map = null;

	private String reduce = null;

	private String finalize = null;

	private final int limit = 1000;

	public static ArrayList<CBSAccessLog> accessLogResults = null;
	
	public static Mongo mongo ;
	
	
	@SuppressWarnings("deprecation")
	private synchronized ArrayList process(List<String> mappingUrls,String dbCollectName) {
		ArrayList<CBSAccessLog> accessLogResults = new ArrayList<CBSAccessLog>();
		Mongo mongo;
		DBCollection dbColl;
		QueryBuilder queryBuilder = new QueryBuilder();
		SimpleDateFormat origDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		SimpleDateFormat finalDateFormat=new SimpleDateFormat("yyyy-MMM-dd");
			try {
				mongo = new Mongo("10.201.10.210");
				System.out.println("collectName is" +dbCollectName);
				dbColl=mongo.getDB("CBSNov").getCollection(dbCollectName.toString());
				
				
				List<DBObject> collIndexList=dbColl.getIndexInfo();
				boolean createResponseTimeIndex=false;
				boolean createActionIndex=false;
				
				for(DBObject collIndex:collIndexList){
					System.out.println(dbColl.getName()+" index info: "+collIndex.get("name"));
					if(collIndex.get("name").toString().contains("responseTime_1_mappingUrl_1_service_1")){
						createResponseTimeIndex=true;
					}
					if(collIndex.get("name").toString().contains("mappingUrl_1_responseTime_1_service_1")){
						
						createActionIndex=true;
					}
					
				}
				createDBColleIndex(dbColl,createResponseTimeIndex,createActionIndex);
				
				for (String urlName : mappingUrls) {

						BasicDBObject query = new BasicDBObject();


						query.put("mappingUrl", urlName);
						

						Date finalDate=new Date();
						int size = dbColl.find(query).size();
							
						
						if (size > 0) {
							
							if(dbColl.findOne(query).containsField("date")){
								Date date=origDateFormat.parse(dbColl.findOne(query).get("date").toString());
//								finalDate.setYear(date.getYear());
//								finalDate.setMonth(date.getMonth());
								finalDate.setDate(date.getDate());
							}

							CBSAccessLog tempResult = mapping(dbColl, urlName,
									null, query, size);
							tempResult.setDate(finalDate);
							tempResult.setLogDateFlag(dbCollectName.toString());
							accessLogResults.add(tempResult);
							ApplicationAggreagationSave task = new ApplicationAggreagationSave();
							ArrayList<CBSAccessLog> _tempResult=new ArrayList();
							_tempResult.add(tempResult);
							task.insert(_tempResult);
						}else{
							System.out.println("Can't find the data: "+urlName+"on "+dbCollectName);
//							CBSAccessLog tempResult=new CBSAccessLog();
//							double dummyDouble=-1.00;
//							String dummString="-1";
////							tempResult.setApi(apiName);
//							tempResult.setAvgResponseDataSize(dummyDouble);
//							tempResult.setAvgResponseTime(dummyDouble);
//							tempResult.setCount_0(dummString);
//							tempResult.setCount_1000(dummString);
//							tempResult.setCount_200(dummString);
//							tempResult.setCount_2000(dummString);
//							tempResult.setCount_3000(dummString);
//							tempResult.setCount_50(dummString);
//							tempResult.setCount_500(dummString);
//							tempResult.setDate(null);
//							tempResult.setLogDateFlag(dbCollectName.toString());
//							tempResult.setMaxResponseTime(dummyDouble);
//							tempResult.setMinResponseTime(dummyDouble);
//							tempResult.setNinetyPercentResp(dummyDouble);
//							tempResult.setTotalHits(dummyDouble);
//							tempResult.setTotalResponseTime(dummyDouble);
//							tempResult.setUrl(dummString);
//							accessLogResults.add(tempResult);
							
						}
					}
				this.setAccessLogResults(accessLogResults);
				ApplicationAggreagationSave task = new ApplicationAggreagationSave();
				task.insert(accessLogResults);
				task.commit();
				return accessLogResults;
			}
			 catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (MongoException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return null;
	}

	

	private void createDBColleIndex(DBCollection dbColl,
			boolean createResponseTimeIndex, boolean createActionIndex) {
		if(!createResponseTimeIndex){
			DBObject objIndex_res_apiName_service=new BasicDBObjectBuilder().add("responseTime",1).add("mappingUrl", 1).add("service",1).get();
			dbColl.createIndex(objIndex_res_apiName_service);
		}
		if(!createActionIndex){
			DBObject objIndex_action_service=new BasicDBObjectBuilder().add("mappingUrl",1).add("responseTime",1).add("service",1).get();
			dbColl.createIndex(objIndex_action_service);
			
		}
	}

	
	public static void build(DataFileMapping dataFileMapping) {
		try {
			mongo = new Mongo("10.201.10.210");
			System.out.println("collectName is" +dataFileMapping.getCollectName());
			String mappingUrlCollectName=dataFileMapping.getDataUrlMappingCollectionName();
			dbColl=mongo.getDB("CBSNov").getCollection(mappingUrlCollectName.toString());
			ApplicationAggregation a=new ApplicationAggregation();
			List<String> urlList=initialMappingUrllist(dbColl);
			System.out.println(urlList.size());
			a.process(urlList,dataFileMapping.getCollectName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
	

	private static List<String> initialMappingUrllist(DBCollection dbColl) {
		DBCursor dbcur=dbColl.find();
		List<String> urlList = new ArrayList<String>();
		while(dbcur.hasNext()){
			String mappingUrl=dbcur.next().get("mappingURL").toString();
			urlList.add(mappingUrl);
		}
		dbcur.close();
		return urlList;
	}



	private void setAccessLogResults(ArrayList<CBSAccessLog> accessLogResults) {

		this.accessLogResults = accessLogResults;
	}

}
