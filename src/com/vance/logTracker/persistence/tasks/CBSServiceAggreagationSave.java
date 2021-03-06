package com.vance.logTracker.persistence.tasks;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


import com.vance.logTracker.domain.AccessLog;
import com.vance.logTracker.domain.CBSAccessLog;
import com.vance.logTracker.persistence.factory.MySqlInstanceFactory;

public class CBSServiceAggreagationSave extends AbstractMySqlTask  implements AccessLogTask {
	private static Connection con=MySqlInstanceFactory.getInstance();

	public CBSServiceAggreagationSave(){
		super();
//		this.con=getConnection();
	}
	
	
	@Override
	public ArrayList query(String logDateFlag,String serviceName) {
		
		ArrayList<CBSAccessLog> returuns=new ArrayList<CBSAccessLog>();
		String sql="select * from cbs where createdDate=(select createdDate from CBS order by createdDate desc limit 1)" +
				"and serviceName='"+serviceName+"' and createdDate is not null";
		try {
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				CBSAccessLog cbsAccessLog=new CBSAccessLog();
				cbsAccessLog.setApi(rs.getString("apiName").toString());
				cbsAccessLog.setDate(Date.valueOf(rs.getString("createdDate")));
				cbsAccessLog.setTotalHits(rs.getDouble("totalHits"));
				cbsAccessLog.setMinResponseTime(rs.getDouble("minResponseTime"));
				cbsAccessLog.setMaxResponseTime(rs.getDouble("maxResponseTime"));
				cbsAccessLog.setAvgResponseTime(rs.getDouble("avgResponseTime"));
				cbsAccessLog.setAvgResponseDataSize(rs.getDouble("avgResponseDataSize"));
				cbsAccessLog.setCount_3000(rs.getBigDecimal("count_3000").toString());
				cbsAccessLog.setCount_2000(rs.getBigDecimal("count_2000").toString());
				cbsAccessLog.setCount_1000(rs.getBigDecimal("count_1000").toString());
				cbsAccessLog.setCount_500(rs.getBigDecimal("count_500").toString());
				cbsAccessLog.setCount_200(rs.getBigDecimal("count_200").toString());
				cbsAccessLog.setCount_50(rs.getBigDecimal("count_50").toString());
				cbsAccessLog.setCount_0(rs.getBigDecimal("count_0").toString());
				cbsAccessLog.setNinetyPercentResp(rs.getInt("ninetyPercentResp"));
				cbsAccessLog.setLogDateFlag(rs.getString("logDateFlag"));
				cbsAccessLog.setService(rs.getString("serviceName"));
				cbsAccessLog.setErrorCount(rs.getInt("erroCount"));
				returuns.add(cbsAccessLog);
				}
			
			return returuns;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
		}
		return null;
	}


	@SuppressWarnings("deprecation")
	@Override
	public <T extends AccessLog> void insert(Collection<T> accessLogs) {
//		Connection con=getConnection();
		Iterator it=accessLogs.iterator();
		
		String sql="insert into CBSServiceByWeek(createdDate,apiName,totalHits,minResponseTime,maxResponseTime,avgResponseTime,avgResponseDataSize," +
				"count_3000,count_2000,count_1000,count_500,count_200,count_50,count_0,ninetyPercentResp,logDateFlag,serviceName) " +
				"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			 ps=con.prepareCall(sql);
			con.setAutoCommit(false);
			while(it.hasNext()){
				CBSAccessLog cbsAccessLog=(CBSAccessLog) it.next();
//				java.sql.Date sqlDate=new java.sql.Date(cbsAccessLog.getDate().getDay(),cbsAccessLog.getDate().getMonth(),cbsAccessLog.getDate().getYear());
//				System.out.println(cbsAccessLog.getDate().getYear());
				java.sql.Date date=new java.sql.Date(cbsAccessLog.getDate().getTime());
				ps.setDate(1,date );
				ps.setString(2, cbsAccessLog.getApi());
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
				ps.setString(16,cbsAccessLog.getLogDateFlag());
				ps.setString(17,cbsAccessLog.getService());
				ps.execute();
			}
//			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				con.commit();
//				ps.close();
//				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public void commit() {
//		try {
//			if(con!=null){
//			con.commit();
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}


	@Override
	public ArrayList queryAll(String queryCon, String applicationName) {
		// TODO Auto-generated method stub
		return null;
	}
}
