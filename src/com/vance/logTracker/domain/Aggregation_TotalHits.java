package com.vance.logTracker.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MapReduceCommand.OutputType;
import com.vance.logTracker.application.MapReduce;

public class Aggregation_TotalHits implements MapReduce {
	
	private String map=null;
	
	private String reduce=null;
	
	private String finalize=null;
	
	
	private DBCollection dbColl ;
	
	public Aggregation_TotalHits(DBCollection dbColl) {
		this.dbColl=dbColl;
	}

	

	@Override
	public void setMap() {
		this.map="function Map(){emit('res',this.responseTime)}";
		
	}

	@Override
	public void setReduce() {
		this.reduce="function (key, values) { var hits = 0; values.forEach(function (val) { hits+=1; }) " +
				"\n"+
				"return hits}";
		
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
		Iterator it=mapReduceOutput.results().iterator();
		
		double result=(Double) mapReduceOutput.results().iterator().next().get("value");
		return result;
	}

	

}
