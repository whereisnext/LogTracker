package com.vance.logTracker.view.summary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ComponentPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.vance.logTracker.persistence.factory.MySqlInstanceFactory;
import com.vance.logTracker.view.component.RfpSummaryPanel;
import com.vance.logTracker.view.summary.base.HomePage;
import com.vance.logTracker.view.summary.base.SummaryPanelModel;

public class SummaryPanel extends Panel {

	public Connection conn = MySqlInstanceFactory.getInstance();

	public HashMap<String, String> summaryResultMap = new HashMap<String, String>();

	private static String defaultQuery = "select createdDate from CBS order by createdDate desc limit 1";

	private static String lastestStartDate;

	private static String lastestEndDate;

	public SummaryPanel(String id, String lastestStartDate,
			String lastestEndDate) {
		super(id);

		this.setLastestStartDate(lastestStartDate);
		this.setLastestEndDate(lastestEndDate);

		
		
	    

		List<ServiceSummary> cbslists = getCBSServcieSummary("CBS");
		add(new Label("applicationName", "Common Business Service"));
		add(new RfpSummaryPanel("rfpSummaryPanel", cbslists));

		List<ServiceSummary> smlists = getApplicationSummary("SM");

		add(new Label("smName", "Supply Management"));
		add(new RfpSummaryPanel("smSummaryPanel", smlists));

		List<ServiceSummary> nexuslists = getApplicationSummary("nexus");

		add(new Label("nexusName", "NEXUS"));
		add(new RfpSummaryPanel("nexusSummaryPanel", nexuslists));

		List<ServiceSummary> epLists = getApplicationSummary("ep");

		add(new Label("epName", "Common Application"));
		add(new RfpSummaryPanel("epSummaryPanel", epLists));

		List<ServiceSummary> amLists = getApplicationSummary("am");

		add(new Label("amName", "Attendee Management"));
		add(new RfpSummaryPanel("amSummaryPanel", amLists));
	}

	public SummaryPanel(String id, SummaryPanelModel summaryPanelModel) {
		// TODO Auto-generated constructor stub
		super(id, summaryPanelModel);

		this.setLastestStartDate(lastestStartDate);
		this.setLastestEndDate(lastestEndDate);
		List<ServiceSummary> cbslists = getCBSServcieSummary("CBS");
		add(new Label("applicationName", "Common Business Service"));
		add(new RfpSummaryPanel("rfpSummaryPanel", cbslists));

		List<ServiceSummary> smlists = getApplicationSummary("SM");

		add(new Label("smName", "Spend Management"));
		add(new RfpSummaryPanel("smSummaryPanel", smlists));

		List<ServiceSummary> nexuslists = getApplicationSummary("nexus");

		add(new Label("nexusName", "NEXUS"));
		add(new RfpSummaryPanel("nexusSummaryPanel", nexuslists));

		List<ServiceSummary> epLists = getApplicationSummary("ep");

		add(new Label("epName", "Common Application"));
		add(new RfpSummaryPanel("epSummaryPanel", epLists));

		List<ServiceSummary> amLists = getApplicationSummary("am");

		add(new Label("amName", "Attendee Management"));
		add(new RfpSummaryPanel("amSummaryPanel", amLists));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ServiceSummary> getCBSServcieSummary(String dbName) {
		List<ServiceSummary> listSummary = new ArrayList<ServiceSummary>();
		String[] servcieNames = { "rfpService", "contactService",
				"vendorService", "useraccountService" };
		for (String serviceName : servcieNames) {
			ServiceSummary serviceSummary = new ServiceSummary();
			String query = "select sum(totalHits) as 'TotalHits',sum(totalHits*avgResponseTime)/sum(totalHits) as 'AvgResponseTime', "
					+ "sum(count_3000)/sum(totalHits)*100 as 'count_3000Percent',"
					+ "sum(count_2000)/sum(totalHits)*100 as 'count_2000Percent',"
					+ "sum(count_1000)/sum(totalHits)*100 as 'count_1000Percent',"
					+ "sum(count_500)/sum(totalHits)*100 as 'count_500Percent',"
					+ "sum(count_50)/sum(totalHits)*100 as 'count_50Percent',"
					+ "sum(count_0)/sum(totalHits)*100 as 'count_0Percent' from "
					+ dbName
					+ " where serviceName= '"
					+ serviceName
					+ "' "
					+ "and createdDate between '"
					+ getLastestStartDate()
					+ "' and '" + getLastestEndDate() + "'";
			System.out.println(query);
			ResultSet rs = null;
			try {
				rs = conn.createStatement().executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (rs.next()) {
					serviceSummary.setAvgRespTime(rs
							.getString("AvgResponseTime"));
					serviceSummary.setTotalHits(rs.getString("TotalHits"));
					serviceSummary.setCount_0Precent(rs
							.getString("count_0Percent"));
					serviceSummary.setCount_1000Percent(rs
							.getString("count_1000Percent"));
					serviceSummary.setCount_3000Percent(rs
							.getString("count_3000Percent"));
					serviceSummary.setCount_2000Percent(rs
							.getString("count_2000Percent"));
					serviceSummary.setCount_500Percent(rs
							.getString("count_500Percent"));
					serviceSummary.setName(serviceName);
					listSummary.add(serviceSummary);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listSummary;

	}

	private List<ServiceSummary> getApplicationSummary(String dbName) {
		List<ServiceSummary> listSummary = new ArrayList<ServiceSummary>();
		String[] servcieNames = { "rfpService", "contactService",
				"vendorService", "useraccountService" };
		// for(String serviceName:servcieNames){
		ServiceSummary serviceSummary = new ServiceSummary();
		String query = "select sum(totalHits) as 'TotalHits',sum(totalHits*avgResponseTime)/sum(totalHits) as 'AvgResponseTime', "
				+ "sum(count_3000)/sum(totalHits)*100 as 'count_3000Percent',"
				+ "sum(count_2000)/sum(totalHits)*100 as 'count_2000Percent',"
				+ "sum(count_1000)/sum(totalHits)*100 as 'count_1000Percent',"
				+ "sum(count_500)/sum(totalHits)*100 as 'count_500Percent',"
				+ "sum(count_50)/sum(totalHits)*100 as 'count_50Percent',"
				+ "sum(count_0)/sum(totalHits)*100 as 'count_0Percent' from "
				+ dbName
				+ " where createdDate between '"
				+ getLastestStartDate() + "' and '" + getLastestEndDate() + "'";
		System.out.println(query);
		ResultSet rs = null;
		try {
			rs = conn.createStatement().executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (rs.next()) {
				serviceSummary.setAvgRespTime(rs.getString("AvgResponseTime"));
				serviceSummary.setTotalHits(rs.getString("TotalHits"));
				serviceSummary
						.setCount_0Precent(rs.getString("count_0Percent"));
				serviceSummary.setCount_1000Percent(rs
						.getString("count_1000Percent"));
				serviceSummary.setCount_3000Percent(rs
						.getString("count_3000Percent"));
				serviceSummary.setCount_2000Percent(rs
						.getString("count_2000Percent"));
				serviceSummary.setCount_500Percent(rs
						.getString("count_500Percent"));
				serviceSummary.setName("N/A");
				listSummary.add(serviceSummary);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listSummary;

	}

	public void setSummaryResultMap(HashMap<String, String> summaryResultMap,
			String key, String value) {
		summaryResultMap.put(key, value);
	}

	public HashMap<String, String> getSummaryResultMap() {
		return this.summaryResultMap;
	}

	public static String getLastestStartDate() {
		return lastestStartDate;
	}

	public static void setLastestStartDate(String lastestStartDate) {
		SummaryPanel.lastestStartDate = lastestStartDate;
	}

	public static String getLastestEndDate() {
		return lastestEndDate;
	}

	public static void setLastestEndDate(String lastestEndDate) {
		SummaryPanel.lastestEndDate = lastestEndDate;
	}

}
