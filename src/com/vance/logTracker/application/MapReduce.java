package com.vance.logTracker.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.vance.logTracker.domain.AccessLog;


public interface MapReduce {
	public void setMap();
	public void setReduce();
	public void setFinilize();
	ArrayList process(List<String> apiNameList,String serviceName);
	

	Double getMapReduceResult(DBCollection dbColl, String apiName,
			String serviceName, DBObject query);
	
}
