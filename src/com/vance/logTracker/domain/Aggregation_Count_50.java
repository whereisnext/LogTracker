package com.vance.logTracker.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MapReduceCommand.OutputType;
import com.mongodb.QueryBuilder;
import com.vance.logTracker.application.MapReduce;

public class Aggregation_Count_50 implements MapReduce {
	
	private String map=null;
	
	private String reduce=null;
	
	private String finalize=null;
	
	
	private DBCollection dbColl ;

	private DBObject query;
	
	public Aggregation_Count_50(DBCollection dbColl) {
		
		this.dbColl=dbColl;
	}

	

	@Override
	public void setMap() {
		this.map="function Map(){emit('res',this.responseTime)}";
		
	}

	@Override
	public void setReduce() {
		this.reduce="function (key, values) { var count = 0; values.forEach(function (val) { if(val>1000 && val<2000) count+=1}) " +
				"\n"+
				"return count}";
		
	}

	@Override
	public void setFinilize() {
		this.finalize="function Finalize(action,values){return values}";
	}

	@Override
	public ArrayList process(List<String> apiNameList, String serviceName) {
		
		return null;
	}



	@Override
	public Double getMapReduceResult(DBCollection dbColl,String apiName, String serviceName,DBObject query) {
		// TODO Auto-generated method stub
		setMap();
		setReduce();
		setFinilize();
		MapReduceCommand cmd=new MapReduceCommand(dbColl,map,reduce,null, OutputType.INLINE,query);
		MapReduceOutput mapReduceOutput = dbColl.mapReduce(cmd);
		QueryBuilder queryBuilder=new QueryBuilder();
		queryBuilder.put("responseTime");
		queryBuilder.greaterThanEquals(50);
		queryBuilder.lessThan(200);
		query.putAll(queryBuilder.get());
		int count=dbColl.find(query).size();
		
//		System.out.println(mapReduceOutput.results());
//		Iterator it=mapReduceOutput.results().iterator();
		
//		double result=(Double) mapReduceOutput.results().iterator().next().get("value");
		return (double) count;
	}

	

}
