package com.vance.logTracker.persistence.tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import com.vance.logTracker.domain.CBSAccessLog;
import com.vance.logTracker.persistence.factory.MySqlInstanceFactory;

public abstract class AbstractMySqlTask implements AccessLogTask {
	
	private static Connection con=MySqlInstanceFactory.getInstance();
	
	
	
	public static Connection getConnection(){
		return con;
	}

	public ArrayList query(String queryCon) {
		return null;
		
	}
	

	
}
