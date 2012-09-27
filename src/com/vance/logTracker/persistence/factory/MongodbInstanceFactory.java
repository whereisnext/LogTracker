package com.vance.logTracker.persistence.factory;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBAddress;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
import com.mongodb.MongoURI;

public class MongodbInstanceFactory {
	
	public static Mongo mongo=initalMongodb();
	
	public static  Mongo initalMongodb(){
		
		try {
			if(mongo==null){
			MongoURI uri=new MongoURI("mongodb://10.201.10.210:27017/accessLogs");
			mongo = (new Mongo.Holder()).singleton().connect(uri);
			return mongo;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void connect(String url){
		
		initalMongodb();
		
	}
	
	public static DB getDBInstance(String dbName){
		DB db;
		try{
		if(mongo!=null){
			db=mongo.getDB(dbName);
			
		}else
		{
			mongo=initalMongodb();

		}
		db=mongo.getDB(dbName);
		db.cleanCursors(false);
		return db;
		
		}finally{
			
		}
		
	}
	
	
}
