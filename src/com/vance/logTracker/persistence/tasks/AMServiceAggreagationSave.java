package com.vance.logTracker.persistence.tasks;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


import com.vance.logTracker.domain.AccessLog;
import com.vance.logTracker.domain.CBSAccessLog;
import com.vance.logTracker.persistence.factory.MySqlInstanceFactory;

public class AMServiceAggreagationSave extends AbstractMySqlTask  implements AccessLogTask {
	private static Connection con=MySqlInstanceFactory.getInstance();

	public AMServiceAggreagationSave(){
		super();
//		this.con=getConnection();
	}
	
	
	


	@SuppressWarnings("deprecation")
	@Override
	public <T extends AccessLog> void insert(Collection<T> accessLogs) {
//		Connection con=getConnection();
		Iterator it=accessLogs.iterator();
		
		String sql="insert into AmApiByDate(createdDate,url,totalHits,minResponseTime,maxResponseTime,avgResponseTime,avgResponseDataSize," +
				"count_3000,count_2000,count_1000,count_500,count_200,count_50,count_0,ninetyPercentResp) " +
				"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			con.setAutoCommit(false);
			PreparedStatement ps=con.prepareCall(sql);
			
			while(it.hasNext()){
				CBSAccessLog cbsAccessLog=(CBSAccessLog) it.next();
				java.sql.Date sqlDate=new java.sql.Date(cbsAccessLog.getDate().getDay(),cbsAccessLog.getDate().getMonth(),cbsAccessLog.getDate().getYear());
//				System.out.println(cbsAccessLog.getDate().getYear());
				ps.setDate(1, new java.sql.Date(cbsAccessLog.getDate().getTime()));
				ps.setString(2, cbsAccessLog.getUrl());
				ps.setInt(3,cbsAccessLog.getTotalHits().intValue());
				ps.setDouble(4, cbsAccessLog.getMinResponseTime());
				ps.setDouble(5,cbsAccessLog.getMaxResponseTime());
				ps.setDouble(6,cbsAccessLog.getAvgResponseTime());
				ps.setDouble(7,cbsAccessLog.getAvgResponseDataSize());
				ps.setDouble(8, Double.valueOf(cbsAccessLog.getCount3000()));
				ps.setDouble(9,Double.valueOf(cbsAccessLog.getCount_2000()));
				ps.setDouble(10,Double.valueOf(cbsAccessLog.getCount_1000()));
				ps.setDouble(11,Double.valueOf(cbsAccessLog.getCount_500()));
				ps.setDouble(12, Double.valueOf(cbsAccessLog.getCount_200()));
				ps.setDouble(13, Double.valueOf(cbsAccessLog.getCount_50()));
				ps.setDouble(14, Double.valueOf(cbsAccessLog.getCount_0()));
				ps.setDouble(15, Double.valueOf(cbsAccessLog.getNinetyPercentResp()).intValue());
				ps.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				con.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}





	@Override
	public ArrayList query(String queryCon, String serviceName) {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public ArrayList queryAll(String queryCon, String applicationName) {
		// TODO Auto-generated method stub
		return null;
	}
}
