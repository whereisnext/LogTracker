package com.vance.logTracker.persistence.factory;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.MySQLConnection;

public class MySqlInstanceFactory {
	
	private static Connection con=initConnection();
	
	public MySqlInstanceFactory(){
		
	}
	
	public static Connection getInstance(){
		return con;
	}

	private static Connection initConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url="jdbc:mysql://10.201.10.210:3306/accessLogs";
			String user="root";
			String password="root";
			Connection con=(Connection) DriverManager.getConnection(url, user, null);
			return con;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String args[]){
		Connection con=MySqlInstanceFactory.getInstance();
		System.out.println(con.getHost());
	}

//	public static Connection getInstance() {
//		try {
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
//			String url="jdbc:mysql://10.201.10.210:3306/applicationAccessLogs";
//			String user="root";
//			String password="root";
//			Connection con=(Connection) DriverManager.getConnection(url, user, null);
//			return con;
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	
}
