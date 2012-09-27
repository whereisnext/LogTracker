package com.vance.logTracker.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.JavaScriptUtils;

import com.vance.logTracker.application.ServiceAggregation;
import com.vance.logTracker.domain.CBSAccessLog;
import com.vance.logTracker.persistence.tasks.ApplicationAggreagationSave;

public class SingleUrlPage extends BasePage {
	
	private ArrayList<CBSAccessLog> lists;
	public SingleUrlPage(PageParameters pagams){
		super(pagams);
		
//		add(new Label("reportTitle","smChartByUrl"));
		
		makingReport(pagams);
		
	}
	
	
	private void makingReport(PageParameters pageParams) {
		String url=pageParams.get("url").toString();
		String applicationName=pageParams.get("application").toString();
		add(new Label("reportTitle",url));

		HashMap<String,? extends ArrayList> urlMappingResult=queryApplicationAccessLogGroupByUrl(url,applicationName);
		
	
		add(new SMSingleUrlAccessLogPanel("smAggregationByUrl",urlMappingResult.get(url)));

//			renderPerformanceChartJs(urlMappingResult.get(url));
		
	}
	
	
	private HashMap<String,? extends ArrayList> queryApplicationAccessLogGroupByUrl(String url,String applicationName) {
		HashMap<String,ArrayList> returnAccessLogMap=new HashMap();
		ArrayList<CBSAccessLog> cbsAccessLogs=new ArrayList<CBSAccessLog>();
		ApplicationAggreagationSave queryTask=new ApplicationAggreagationSave();
		cbsAccessLogs=queryTask.queryAll(url,applicationName);
		returnAccessLogMap.put(url,cbsAccessLogs);
		return returnAccessLogMap;
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
				pageResponse.write("});})();");
				pageResponse.write(JavaScriptUtils.SCRIPT_CLOSE_TAG);

//				response.renderJavaScriptReference("YUI().use('node', function (Y) {var node = Y.one('#myRunning');node.hide();})");
//				response.renderJavaScriptReference("alert('aaa')");
				
			}

			@Override
			protected void respond(AjaxRequestTarget target) {

			}

		});

	}

}
