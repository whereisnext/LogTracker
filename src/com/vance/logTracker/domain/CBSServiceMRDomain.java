package com.vance.logTracker.domain;

import java.util.Iterator;
import java.util.regex.Pattern;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.vance.logTracker.application.MapReduce;

public class CBSServiceMRDomain extends BaseAPIDomain{
	
	private CBSAccessLog cbsAccessLog=new CBSAccessLog();
	
	protected static DBCollection dbColl;
	
	private MapReduceCommand cmd;
	
	private DBObject query;

	private String apiNameList;

	private String serviceName;
	
	protected CBSAccessLog mapping(DBCollection dbColl,String apiNameList,String serviceName,DBObject query,int totalSize) {
			this.apiNameList=apiNameList;
			this.serviceName=serviceName;
		    this.query=query;
			this.dbColl=dbColl;
			double maxRes=getMax();
			double minRes=getMin();
//			double totalHits=getTotalHits();
			double totalHits=totalSize;
			double totalRes=getTotalRes();
			double totalResDataSize=getTotalResDataSize();
			double count_3000=getCount_3000();
			double count_2000=getCount_2000();
			double count_1000=getCount_1000();
			double count_500=getCount_500();
			double count_200=getCount_200();
			double count_50=getCount_50();
			double count_0=getCount_0();
			double avgRes=totalRes/totalHits;
			double avgResDataSize=totalResDataSize/totalHits;
			double ninetyPercentLine=generate90Line(totalHits,apiNameList,query);
			
			CBSAccessLog cbsAccessLog=new CBSAccessLog();
			cbsAccessLog.setApi(apiNameList);
			cbsAccessLog.setMaxResponseTime(maxRes);
			cbsAccessLog.setMinResponseTime(minRes);
			cbsAccessLog.setTotalHits(totalHits);
			cbsAccessLog.setTotalResponseTime(totalRes);
			cbsAccessLog.setCount_3000(String.valueOf(count_3000));
			cbsAccessLog.setCount_2000(String.valueOf(count_2000));
			cbsAccessLog.setCount_1000(String.valueOf(count_1000));
			cbsAccessLog.setCount_500(String.valueOf(count_500));
			cbsAccessLog.setCount_200(String.valueOf(count_200));
			cbsAccessLog.setCount_50(String.valueOf(count_50));
			cbsAccessLog.setCount_0(String.valueOf(count_0));
			cbsAccessLog.setNinetyPercentResp(Double.valueOf(ninetyPercentLine));
			cbsAccessLog.setAvgResponseDataSize(avgResDataSize);
			cbsAccessLog.setAvgResponseTime(avgRes);
			return cbsAccessLog;
	}
	
	private double getMax(){
		Aggregation_Max getMax=new Aggregation_Max(dbColl);
		return getMax.getMapReduceResult(dbColl,apiNameList, serviceName,query);
	}
	
	private double getMin(){
		Aggregation_Min getMin=new Aggregation_Min(dbColl);
		return getMin.getMapReduceResult(dbColl,apiNameList, serviceName,query);
	}
	
	
	private double getTotalHits(){
		Aggregation_TotalHits getMax=new Aggregation_TotalHits(dbColl);
		return getMax.getMapReduceResult(dbColl,apiNameList, serviceName,query);
	}
	
	private double getTotalRes(){
		Aggregation_TotalRes getMin=new Aggregation_TotalRes(dbColl);
		return getMin.getMapReduceResult(dbColl,apiNameList, serviceName,query);
	}
	
	private double getTotalResDataSize(){
		Aggregation_TotalResDataSize getMax=new Aggregation_TotalResDataSize(dbColl);
		return getMax.getMapReduceResult(dbColl,apiNameList, serviceName,query);
	}
	
	private double getCount_3000(){
		Aggregation_Count_3000 getMin=new Aggregation_Count_3000(dbColl);
		return getMin.getMapReduceResult(dbColl,apiNameList, serviceName,query);
	}
	
	private double getCount_2000(){
		Aggregation_Count_2000 getMin=new Aggregation_Count_2000(dbColl);
		return getMin.getMapReduceResult(dbColl,apiNameList, serviceName,query);
	}
	
	private double getCount_1000(){
		Aggregation_Count_1000 getMax=new Aggregation_Count_1000(dbColl);
		return getMax.getMapReduceResult(dbColl,apiNameList, serviceName,query);
	}
	
	private double getCount_500(){
		Aggregation_Count_500 getMin=new Aggregation_Count_500(dbColl);
		return getMin.getMapReduceResult(dbColl,apiNameList, serviceName,query);
	}
	
	private double getCount_200(){
		Aggregation_Count_200 getMin=new Aggregation_Count_200(dbColl);
		return getMin.getMapReduceResult(dbColl,apiNameList, serviceName,query);
	}
	
	private double getCount_50(){
		Aggregation_Count_50 getMax=new Aggregation_Count_50(dbColl);
		return getMax.getMapReduceResult(dbColl,apiNameList, serviceName,query);
	}
	
	private double getCount_0(){
		Aggregation_Count_0 getMin=new Aggregation_Count_0(dbColl);
		return getMin.getMapReduceResult(dbColl,apiNameList, serviceName,query);
	}
	
	
	private double generate90Line(Double totalHits, String apiName,DBObject query) {
		if (totalHits != null && totalHits > 10) {
			// if(this.dbColl==null){
			//
			// }
			
			query.put("action",
					java.util.regex.Pattern.compile("" + apiName + ""));
			int numToLimit = (int) (totalHits * 0.9);
			int numToSkip = (int) (totalHits - numToLimit);
			int batchSize = 1;
			DBCursor dbCur = dbColl
					.find(new BasicDBObject().append("action", apiName))
					.sort(new BasicDBObject("responseTime", -1))
					.limit(numToLimit).skip(numToSkip);
//			Iterator<DBObject> it = dbCur.iterator();
			double ninetyPercent=Double.valueOf(dbCur.next().get("responseTime").toString());
			return ninetyPercent;
		} else {
			return -1;
		}
	}
	
}