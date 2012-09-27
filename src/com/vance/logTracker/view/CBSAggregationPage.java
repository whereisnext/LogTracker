package com.vance.logTracker.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.JavaScriptUtils;

import com.mysql.jdbc.Connection;
import com.vance.logTracker.application.ServiceAggregation;
import com.vance.logTracker.domain.CBSAccessLog;
import com.vance.logTracker.persistence.factory.MySqlInstanceFactory;
import com.vance.logTracker.persistence.tasks.CBSApiAggreagationSave;
import com.vance.logTracker.persistence.tasks.CBSServiceAggreagationSave;
import com.vance.logTracker.persistence.tasks.QueryCondition;
import com.vance.logTracker.util.DateByWeek;
import com.vance.logTracker.util.DatebyDay;
import com.vance.logTracker.util.TestSamples;
import com.vance.logTracker.view.summary.CBSSummaryPage;

public class CBSAggregationPage extends BasePage {
	private ArrayList<CBSAccessLog> lists;
	
	private String serviceName="rfpService";
	
	private WebMarkupContainer resulttable;

	public <T> CBSAggregationPage(){
		super();
		Connection con=MySqlInstanceFactory.getInstance();
		ResultSet rs;
		try {
			rs = con.createStatement().executeQuery("select createdDate from CBS order by createdDate desc limit 1");
			PageParameters pageParams=new PageParameters();
			if(rs.next()){
			String lastestDate=rs.getString("createdDate");
			pageParams.add("aggDate",lastestDate);
			add(new Label("aggDate",lastestDate));
			makingReport(pageParams);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public CBSAggregationPage(PageParameters pageParams){
		super(pageParams);
		Connection con=MySqlInstanceFactory.getInstance();
		ResultSet rs;
		try {
			rs = con.createStatement().executeQuery("select createdDate from CBS order by createdDate desc limit 1");
			if(rs.next()){
			String lastestDate=rs.getString("createdDate");
			add(new Label("aggDate",lastestDate));
			makingReport(pageParams);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void makingReport(PageParameters pageParams) {
		if(pageParams.isEmpty()){
			pageParams.add("aggDate","");
		}
		Form form=new Form("cbsAggregationForm");
		String aggDate=pageParams.get("aggDate").toString();
//		form.add(new Label("aggDate",aggDate));
		form.add(new Label("reportTitle",aggDate));
		setLists(ServiceAggregation.accessLogResults);
		
		this.resulttable = new WebMarkupContainer("resulttable");
		this.resulttable.setVisible(false);
		form.add(resulttable);
		HashMap<String,? extends ArrayList> rfpMaps=queryRfpAccessLogGroupByWeek("rfpService",aggDate);
		HashMap<String,? extends ArrayList> uasMaps=queryRfpAccessLogGroupByWeek("userAccountService",aggDate);
		HashMap<String,? extends ArrayList> contactMaps=queryRfpAccessLogGroupByWeek("contactService",aggDate);
		HashMap<String,? extends ArrayList> vendorMaps=queryRfpAccessLogGroupByWeek("vendorService",aggDate);
		form.add(new RfpAccessLogPage("RfpServiceAggregation",rfpMaps.get(aggDate)));
		form.add(new UasAccessLogPage("UasServiceAggregation",uasMaps.get(aggDate)));
		form.add(new ContactAccessLogPage("ContactServiceAggregation",contactMaps.get(aggDate)));
		form.add(new VendorAccessLogPage("VenodorServiceAggregation",vendorMaps.get(aggDate)));
		if (getLists() != null) {

			renderPerformanceChartJs(getLists());
		}
		form.add(new AjaxButton("backSummary"){
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				setResponsePage(CBSSummaryPage.class);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				
			}
		}.setVisible(false));
		add(form);
	}


	private HashMap<String,? extends ArrayList> queryRfpAccessLogGroupByWeek(String serviceName,String logDateFlag) {
		HashMap<String,ArrayList> returnAccessLogMap=new HashMap();
		ArrayList<CBSAccessLog> cbsAccessLogs=new ArrayList<CBSAccessLog>();
		CBSServiceAggreagationSave queryTask=new CBSServiceAggreagationSave();
		cbsAccessLogs=queryTask.query(logDateFlag,serviceName);
		returnAccessLogMap.put(logDateFlag,cbsAccessLogs);
		return returnAccessLogMap;
	}
	
	
	private HashMap<String,? extends ArrayList> queryRfpAccessLogGroupByDay() {
		HashMap<String,ArrayList> returnAccessLogMap=new HashMap();
		String serviceName="rfpService";
		for(DatebyDay dateByDay:DatebyDay.values()){
		ArrayList<CBSAccessLog> cbsAccessLogs=new ArrayList<CBSAccessLog>();
		CBSServiceAggreagationSave queryTask=new CBSServiceAggreagationSave();
		String logDateFlag=dateByDay.toString();
		cbsAccessLogs=queryTask.query(logDateFlag,serviceName);
		returnAccessLogMap.put(logDateFlag,cbsAccessLogs);
		}
		return returnAccessLogMap;
	}


	public ArrayList<CBSAccessLog> getLists() {
		return lists;
	}

	public void setLists(ArrayList<CBSAccessLog> lists) {

		this.lists = lists;
	}
	
	
	
	private void renderPerformanceChartJs(ArrayList<CBSAccessLog> lists) {
		final ArrayList<CBSAccessLog> accessLogResultSet = lists;
		add(new AbstractDefaultAjaxBehavior() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1337563249301309126L;
			private ResourceReference demoJs = new JavaScriptResourceReference(
					RfpAccessLogPage.class, "test.js");

			@Override
			public void beforeRender(Component component) {
				//
				// component.getResponse().write(JavaScriptUtils.SCRIPT_OPEN_TAG);
				// component.getResponse().write("var node = Y.one('#myRunning');alert(node);");
				// component.getResponse().write(JavaScriptUtils.SCRIPT_CLOSE_TAG);
			}

			@Override
			public void renderHead(Component component, IHeaderResponse response) {
	
				response.renderOnDomReadyJavaScript("YUI().use('node', function (Y) {var node = Y.one('#myRunning');node.show();})");

				Response pageResponse = component.getResponse();
				pageResponse.write(JavaScriptUtils.SCRIPT_OPEN_TAG);
				pageResponse.write("(function(myvalues) {"
						+ "YUI().use('charts', function (Y) " + "{");
				pageResponse.write(" var myDataValues = [ ");
				// pageResponse.write("{");
				String str = ",";
				int totalSize = accessLogResultSet.size();
				for (int i = 0; i < totalSize; i++) {
					if (i >= totalSize - 1) {
						str = "";
					}
					Random r = new Random();
					int j = r.nextInt(1000);

					pageResponse.write("{category:'"
							+ accessLogResultSet.get(i).getApi().toString()
							+ "', " + "ninetyPercentResp: "
							+ accessLogResultSet.get(i).getNinetyPercentResp()
							+ "}" + str);

				}
				pageResponse.write("];");
				pageResponse
						.write("var mychartStyle = {axes:{    values:{label:{    rotation:-45,    color:'#ff0000'}    },  "
								+ "  date:{label:{    rotation:-45,    color: '#ff0000'}    }},series:{   "
								+ " international:{marker:{    fill:{        color:'#ff8888'    },    border:{        color:'#ff0000'    },  "
								+ "  over:{        fill:{            color:'#ffffff'        },      "
								+ "  border:{            color:'#fe0000'        },        width: 199,      "
								+ "  height: 112    }},line:{    color:'#ff0000'}    },    expenses:{line:{    color:'#999'},marker:{   "
								+ " fill:{        color:'#ddd'    },    border:{        color:'#999'    },    over: {        fill: {   "
								+ "         color: '#eee'        },        border: {            color: '#000'        },     "
								+ "   width: 100,        height: 100    }}    },    domestic: {marker: {    over: {      "
								+ "  fill: {            color: '#eee'        },        width: 100,        height: 100    }}    }}};");
				pageResponse
						.write("var myAxes={financials:{keys:[\"ninetyPercentResp\"],position:\"right\",type:\"numeric\",styles:{majorTicks:"
								+ "{display:\"none\"}}},dateRange:{keys:[\"category\"],"
								+ "position:\"bottom\",type:\"category\",styles:{majorTicks:{display:\"none\"},label:{rotation:-25,margin:{top:5}}}}};");
				pageResponse
						.write("var seriesCollection=[{type:\"column\",xAxis:\"dateRange\",yAxis:\"financials\",xKey:\"category\","
								+ "yKey:\"ninetyPercentResp\",xDisplayName:\"ApiName\",yDisplayName:\"ninetyPercentResp(ms)\","
								+ "styles:{border:{weight:5,color:\"#58006e\"},over:{fill:{alpha:0.7}}}}];");

				pageResponse
						.write("var myChart=new Y.Chart({dataProvider:myDataValues,"
								+ "axes:myAxes,seriesCollection:seriesCollection,"
								+ "horizontalGridlines:true,verticalGridlines:true,render:\"#mychart\"});");
				// pageResponse.write("});");
				pageResponse.write("});})();");
				// pageResponse
				// .write("  var myAxes = {financials:{keys:['miscellaneous', 'revenue', 'expenses'],"
				// +
				// "position:'right',type:'numeric',styles:{majorTicks:{display: 'none'}}},dateRange:{keys:['date'],position:'bottom',type:'category',styles:{majorTicks:{display: 'none'},label: {rotation:-25,margin:{top:5}}}}};//define the series var seriesCollection = [ {type:'column',xAxis:'dateRange',yAxis:'financials',xKey:'date',yKey:'miscellaneous',xDisplayName:'Date',yDisplayName:'Miscellaneous',styles: {border: {weight: 5,color: '#58006e'},over: {fill: {alpha: 0.7}}}},{type:'column',xAxis:'dateRange',yAxis:'financials',xKey:'date',yKey:'expenses',yDisplayName:'Expenses',styles: {marker:{fill: {color: '#e0ddd0' },border: {weight: 1,color: '#cbc8ba'},over: {fill: {alpha: 0.7}}}}},{type:'combo',xAxis:'dateRange',yAxis:'financials',xKey:'date',yKey:'revenue',xDisplayName:'Date',yDisplayName:'Deductions',line: {color: '#ff7200'},marker: {fill: {color: '#ff9f3b'},border: {color: '#ff7200',weight: 1},over: {width: 12,height: 12},width:9,height:9}}];//instantiate the chartvar myChart = new Y.Chart({dataProvider:myDataValues, axes:myAxes, seriesCollection:seriesCollection, horizontalGridlines: true,verticalGridlines: true,render:'#mychart'});});");
				pageResponse.write(JavaScriptUtils.SCRIPT_CLOSE_TAG);

//				response.renderOnDomReadyJavaScript("YUI().use('node', function (Y) {var node = Y.one('#myRunning');node.hide();})");
//				response.renderJavaScriptReference("YUI().use('node', function (Y) {var node = Y.one('#myRunning');node.hide();})");
				
			}

			@Override
			protected void respond(AjaxRequestTarget target) {
				// TODO Auto-generated method stub

			}

		});

	}

	@Override
	public void onAfterRender() {
		super.onAfterRender();
		System.out.println("Finished rendering..");
	}

	@Override
	public void onBeforeRender() {
		super.onBeforeRender();
//		System.out.println("basepage.onBeforeRender()");
//
//		System.out.println("cbsaccessLogpage.onBeforeRender()");
//		if (ServiceAggregation.accessLogResults == null) {
//			label.setVisible(true);
//			// chart.setVisible(false);
//		} else {
//			System.out.println("finish flash....");
//			System.out.println(ServiceAggregation.accessLogResults.size());
//			fullViewLabel.setVisible(false);
//			ajaxSelfFlash.stop();
//		}
	}
}
