package com.vance.logTracker.persistence.tasks;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;

import com.vance.logTracker.domain.AccessLog;

public interface AccessLogTask {
	
	public <T extends AccessLog> void insert(Collection<T> accessLogs);
	
	public ArrayList query(String queryCon,String applicationName);
	
	public ArrayList queryAll(String queryCon,String applicationName);
}
