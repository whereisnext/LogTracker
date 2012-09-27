package com.vance.logTracker.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.JavaScriptUtils;
import org.apache.wicket.util.string.StringValue;

import com.vance.logTracker.application.APIAggregation;
import com.vance.logTracker.application.ServiceAggregation;
import com.vance.logTracker.domain.CBSAccessLog;
import com.vance.logTracker.domain.CBSApiDomain;
import com.vance.logTracker.persistence.tasks.ApplicationAggreagationSave;
import com.vance.logTracker.persistence.tasks.CBSApiAggreagationSave;
import com.vance.logTracker.persistence.tasks.CBSServiceAggreagationSave;

public class SingleApiPage extends BasePage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 135299029898181440L;
	
	private ArrayList<CBSAccessLog> lists=APIAggregation.apiLevelResults;
	
	private int totalDays=10;
	
	private String apiName;
	
	public SingleApiPage(){
		super();
		
	}
	
	public SingleApiPage(PageParameters pageParams){
		super(pageParams);

		apiName=pageParams.get("apiName").toString();
		add(new Label("reportTitle",apiName));
		String logDateFlag=pageParams.get("logDateFlag").toString();
		ArrayList<CBSAccessLog> cbsAccessLogs=new ArrayList<CBSAccessLog>();
		
		HashMap<String,? extends ArrayList> apiMappingResult=queryAPIAccessLogByAPiName(apiName);

		CBSApiAggreagationSave apiQuery=new CBSApiAggreagationSave();
		
		cbsAccessLogs=apiQuery.query(apiName);
		
		add(new APIAccessLogPanel("apiAggregation",apiMappingResult.get(apiName)));
		
//		renderPerformanceChartJs(cbsAccessLogs);
		
		
	}
	
	
	private HashMap<String,? extends ArrayList> queryAPIAccessLogByAPiName(String apiName) {
		HashMap<String,ArrayList> returnAccessLogMap=new HashMap();
		ArrayList<CBSAccessLog> cbsAccessLogs=new ArrayList<CBSAccessLog>();
		CBSApiAggreagationSave queryTask=new CBSApiAggreagationSave();
		cbsAccessLogs=queryTask.query(apiName);
		returnAccessLogMap.put(apiName,cbsAccessLogs);
		return returnAccessLogMap;
	}
	
	
	private void renderPerformanceChartJs(ArrayList<CBSAccessLog> lists) {
		final ArrayList<CBSAccessLog> accessLogResultSet=lists;
		add(new AbstractDefaultAjaxBehavior() {
			private ResourceReference demoJs = new JavaScriptResourceReference(
					RfpAccessLogPage.class, "test.js");
			
			@Override
			public void beforeRender(Component component){
				
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
					if (i >= totalSize-1) {
						str = "";
					}
					Random r=new Random();
					int j=r.nextInt(1000);
//					pageResponse.write("{date:'1/" + i
//							+ "/2011', miscellaneous:" + (j + 300 + 500)
//							+ ", expenses:"+j+"}" + str);

					pageResponse.write("{date:'"+accessLogResultSet.get(i).getDate().toString()+"', " +
							"ninetyPercentResp: "+accessLogResultSet.get(i).getNinetyPercentResp()+",maxResp:'"+accessLogResultSet.get(i).getMaxResponseTime()+"',avgResp:'"+accessLogResultSet.get(i).getAvgResponseTime()
								+"'}"+str);
					
				}
				pageResponse.write("];");
				pageResponse.write("var mychartStyle = {axes:{    values:{label:{    rotation:-45,    color:'#ff0000'}    },  " +
						"  date:{label:{    rotation:-45,    color: '#ff0000'}    }},series:{   " +
						" international:{marker:{    fill:{        color:'#ff8888'    },    border:{        color:'#ff0000'    },  " +
						"  over:{        fill:{            color:'#ffffff'        },      " +
						"  border:{            color:'#fe0000'        },        width: 199,      " +
						"  height: 112    }},line:{    color:'#ff0000'}    },    expenses:{line:{    color:'#999'},marker:{   " +
						" fill:{        color:'#ddd'    },    border:{        color:'#999'    },    over: {        fill: {   " +
						"         color: '#eee'        },        border: {            color: '#000'        },     " +
						"   width: 100,        height: 100    }}    },    domestic: {marker: {    over: {      " +
						"  fill: {            color: '#eee'        },        width: 100,        height: 100    }}    }}};");
				pageResponse.write("var myAxes={financials:{keys:[\"maxResp\",\"avgResp\",\"ninetyPercentResp\"],position:\"right\",type:\"numeric\",styles:{majorTicks:{display:\"none\"}}},dateRange:{keys:[\"date\"],position:\"bottom\",type:\"category\",styles:{majorTicks:{display:\"none\"},label:{rotation:-45,margin:{top:5}}}}};");
				pageResponse.write("var seriesCollection=[{type:\"column\",xAxis:\"dateRange\",yAxis:\"financials\",xKey:\"date\",yKey:\"maxResp\",xDisplayName:\"Date\",yDisplayName:\"maxResp\",styles:{border:{weight:1,color:\"#58006e\"},over:{fill:{alpha:0.7}}}},{type:\"column\",xAxis:\"dateRange\",yAxis:\"financials\",xKey:\"date\",yKey:\"avgResp\",yDisplayName:\"avgResp\",styles:{marker:{fill:{color:\"#e0ddd0\"},border:{weight:1,color:\"#cbc8ba\"},over:{fill:{alpha:0.7}}}}},{type:\"combo\",xAxis:\"dateRange\",yAxis:\"financials\",xKey:\"date\",yKey:\"ninetyPercentResp\",xDisplayName:\"Date\",yDisplayName:\"90%Line\",line:{color:\"#ff7200\"},marker:{fill:{color:\"#ff9f3b\"},border:{color:\"#ff7200\",weight:1},over:{width:12,height:12},width:9,height:9}}];");
				
				 pageResponse.write("var myChart=new Y.Chart({dataProvider:myDataValues," +
				 		"axes:myAxes,seriesCollection:seriesCollection," +
				 		"horizontalGridlines:true,verticalGridlines:true,render:\"#mychart\"});");
//				 pageResponse.write("});");
				 pageResponse.write("});})();");
//				pageResponse
//						.write("  var myAxes = {financials:{keys:['miscellaneous', 'revenue', 'expenses']," +
//								"position:'right',type:'numeric',styles:{majorTicks:{display: 'none'}}},dateRange:{keys:['date'],position:'bottom',type:'category',styles:{majorTicks:{display: 'none'},label: {rotation:-25,margin:{top:5}}}}};//define the series var seriesCollection = [ {type:'column',xAxis:'dateRange',yAxis:'financials',xKey:'date',yKey:'miscellaneous',xDisplayName:'Date',yDisplayName:'Miscellaneous',styles: {border: {weight: 5,color: '#58006e'},over: {fill: {alpha: 0.7}}}},{type:'column',xAxis:'dateRange',yAxis:'financials',xKey:'date',yKey:'expenses',yDisplayName:'Expenses',styles: {marker:{fill: {color: '#e0ddd0' },border: {weight: 1,color: '#cbc8ba'},over: {fill: {alpha: 0.7}}}}},{type:'combo',xAxis:'dateRange',yAxis:'financials',xKey:'date',yKey:'revenue',xDisplayName:'Date',yDisplayName:'Deductions',line: {color: '#ff7200'},marker: {fill: {color: '#ff9f3b'},border: {color: '#ff7200',weight: 1},over: {width: 12,height: 12},width:9,height:9}}];//instantiate the chartvar myChart = new Y.Chart({dataProvider:myDataValues, axes:myAxes, seriesCollection:seriesCollection, horizontalGridlines: true,verticalGridlines: true,render:'#mychart'});});");
				pageResponse.write(JavaScriptUtils.SCRIPT_CLOSE_TAG);
				
				response.renderOnDomReadyJavaScript("YUI().use('node', function (Y) {var node = Y.one('#myRunning');node.hide();})");

			}

			@Override
			protected void respond(AjaxRequestTarget target) {
				// TODO Auto-generated method stub
				
			}

		});

	}
	@Override
	public void onAfterRender(){
		super.onAfterRender();
		System.out.println("Finished rendering..");
	}

}
