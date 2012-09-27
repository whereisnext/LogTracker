package com.vance.logTracker.view;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.apache.wicket.resource.dependencies.ResourceDependentResourceReference;
import org.apache.wicket.util.string.JavaScriptUtils;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.mongodb.DB;
import com.vance.logTracker.application.LogTrackerApplication;
import com.vance.logTracker.application.ServiceAggregation;
import com.vance.logTracker.domain.CBSAccessLog;

public class UasAccessLogPage extends Panel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6266516604244877092L;

	private ArrayList<CBSAccessLog> lists=ServiceAggregation.accessLogResults;

	private static Label label = null;

	private static AjaxSelfUpdatingTimerBehavior ajaxSelfFlash;
	
	private String panelId;
	
	private Label fullViewLabel;

	public UasAccessLogPage(final String id,ArrayList lists) {
		super(id);
		
		this.panelId=id;
		
//		add(ajaxSelfFlash);
//		setLists(ServiceAggregation.accessLogResults);
		setLists(lists);
		if (lists != null) {
			System.out.println(lists.size());

		}
//		ajaxSelfFlash=new AjaxSelfUpdatingTimerBehavior(
//				Duration.milliseconds(5));
//		MyModel lists=new MyModel();
		// BaseDomain baseDomain = new BaseDomain();
		// List<String> apiLists = new ArrayList<String>();
		// Collection<? extends String> apiTestSamples = TestSamples.get();
		// apiLists.addAll(apiTestSamples);
		//
		// baseDomain.queryObject(apiLists);
		//
		// ArrayList<CBSAccessLog> lists = baseDomain.getAccessLogResults();

		//
		// if (lists != null) {
		//
		// }

		// lists.add(new CBSAccessLog());
		
		add(new Label("uasChart","uasChart"));
		
		if (getLists() != null) {

			add(new AbstractDefaultAjaxBehavior() {
				private ResourceReference demoJs = new JavaScriptResourceReference(
						UasAccessLogPage.class, "test.js");

				@Override
				public void renderHead(Component component, IHeaderResponse response) {
					// PageParameters pageParameters=new PageParameters();
					// pageParameters.add("date", "5/1/2010");
					// System.out.println(pageParameters.getValues("date"));
					// //response.renderOnDomReadyJavaScript("alert('hello')"); //on
					// Load
					// response.renderJavaScriptReference(demoJs,pageParameters,null,false,null);
					// //include js file
					// // response.renderJavaScript(demoJs.toString(), null);
					// //<script> tag
//					response.renderJavaScriptReference("yui-min.js");
					response.renderOnDomReadyJavaScript("YUI().use('node', function (Y) {var node = Y.one('#myRunning');node.show();})");

					Response pageResponse = component.getResponse();
					pageResponse.write(JavaScriptUtils.SCRIPT_OPEN_TAG);
					pageResponse.write("(function(myvalues) {"
							+ "YUI().use('charts', function (Y) " + "{");
					pageResponse.write(" var myDataValues = [ ");
					// pageResponse.write("{");
					String str = ",";
					int totalSize = getLists().size();
					for (int i = 0; i < totalSize; i++) {
						if (i >= totalSize - 1) {
							str = "";
						}
						Random r = new Random();
						int j = r.nextInt(1000);

						pageResponse.write("{category:'"
								+ getLists().get(i).getApi().toString()
								+ "', " + "ninetyPercentResp: "
								+ getLists().get(i).getNinetyPercentResp()
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
									+ "horizontalGridlines:true,verticalGridlines:true,render:\"#uasChart\"});");
					// pageResponse.write("});");
					pageResponse.write("});})();");
					// pageResponse
					// .write("  var myAxes = {financials:{keys:['miscellaneous', 'revenue', 'expenses'],"
					// +
					// "position:'right',type:'numeric',styles:{majorTicks:{display: 'none'}}},dateRange:{keys:['date'],position:'bottom',type:'category',styles:{majorTicks:{display: 'none'},label: {rotation:-25,margin:{top:5}}}}};//define the series var seriesCollection = [ {type:'column',xAxis:'dateRange',yAxis:'financials',xKey:'date',yKey:'miscellaneous',xDisplayName:'Date',yDisplayName:'Miscellaneous',styles: {border: {weight: 5,color: '#58006e'},over: {fill: {alpha: 0.7}}}},{type:'column',xAxis:'dateRange',yAxis:'financials',xKey:'date',yKey:'expenses',yDisplayName:'Expenses',styles: {marker:{fill: {color: '#e0ddd0' },border: {weight: 1,color: '#cbc8ba'},over: {fill: {alpha: 0.7}}}}},{type:'combo',xAxis:'dateRange',yAxis:'financials',xKey:'date',yKey:'revenue',xDisplayName:'Date',yDisplayName:'Deductions',line: {color: '#ff7200'},marker: {fill: {color: '#ff9f3b'},border: {color: '#ff7200',weight: 1},over: {width: 12,height: 12},width:9,height:9}}];//instantiate the chartvar myChart = new Y.Chart({dataProvider:myDataValues, axes:myAxes, seriesCollection:seriesCollection, horizontalGridlines: true,verticalGridlines: true,render:'#mychart'});});");
					pageResponse.write(JavaScriptUtils.SCRIPT_CLOSE_TAG);

//					response.renderOnDomReadyJavaScript("YUI().use('node', function (Y) {var node = Y.one('#myRunning');node.hide();})");
//					response.renderJavaScriptReference("YUI().use('node', function (Y) {var node = Y.one('#myRunning');node.hide();})");
//					response.renderJavaScriptReference("alert('aaa')");
					
				}

				@Override
				protected void respond(AjaxRequestTarget target) {
					// TODO Auto-generated method stub

				}

			});
		}
		
		
		
		
		
		ListView<Object> listView= new ListView<Object>("uaslistview", lists) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -3788335057293853900L;

			protected void populateItem(ListItem item) {
				final CBSAccessLog cbsAccessLog = (CBSAccessLog) item
						.getModelObject();
				// CBSAccessLog cbsAccessLog=new CBSAccessLog();
				// cbsAccessLog.setApi("vanceTest");
				// cbsAccessLog.setAvgResponseDataSize(10.000);
				// cbsAccessLog.setAvgResponseTime(100.000);
				// cbsAccessLog.setCount_1000("1000");
				// cbsAccessLog.setCount_3000("2000");
				// cbsAccessLog.setMaxResponseTime(10000.00);
				// cbsAccessLog.setMinResponseTime(10);
				// cbsAccessLog.setNinetyPercentResp(50.00);
				// cbsAccessLog.setTotalHits(1000.00);
				// item.add(new Label("apiName", cbsAccessLog.getApi()));

				AjaxLink apiNameLink = new AjaxLink("apiName") {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						PageParameters parameters = new PageParameters();
						parameters.set("apiName", cbsAccessLog.getApi());
						setResponsePage(new SingleApiPage(parameters));

					}

				};

				apiNameLink
						.add(new Label("apiNameValue", cbsAccessLog.getApi()));
				item.add(apiNameLink);

				item.add(new Label("maxResponseTime", cbsAccessLog
						.getMaxResponseTime().toString()));
				item.add(new Label("avgResponseTime", cbsAccessLog
						.getAvgResponseTime().toString()));
				item.add(new Label("minResponseTime", String
						.valueOf(cbsAccessLog.getMinResponseTime())));
				item.add(new Label("totalHits", String.valueOf(cbsAccessLog.getTotalHits().intValue())));
				item.add(new Label(
						"ninetyPercentTime",
						String.valueOf(cbsAccessLog.getNinetyPercentResp() == -1 ? "N/A"
								: cbsAccessLog.getNinetyPercentResp())));
				item.add(new Label("count_3000", String.valueOf(cbsAccessLog
						.getCount3000())));
				item.add(new Label("avgResponseDataSize", String
						.valueOf(cbsAccessLog.getAvgResponseDataSize())));

				item.add(new Label("count_2000", cbsAccessLog.getCount_2000()));
				
				item.add(new Label("count_1000", cbsAccessLog.getCount_1000()));
				
				item.add(new Label("count_500", cbsAccessLog.getCount_500()));

				item.add(new Label("count_200", cbsAccessLog.getCount_200()));

				item.add(new Label("count_50", cbsAccessLog.getCount_50()));

				item.add(new Label("count_0", cbsAccessLog.getCount_0()));

				item.add(new Label("errorCount", String.valueOf(cbsAccessLog.getErrorCount())));
				
				// item.add(new Label("date", cbsAccessLog.getDate()));

			}
		};
		
		add(listView);
		
		

	}

	private void renderPerformanceChartJs(ArrayList<CBSAccessLog> lists) {
		final ArrayList<CBSAccessLog> accessLogResultSet = lists;
		add(new AbstractDefaultAjaxBehavior() {
			private ResourceReference demoJs = new JavaScriptResourceReference(
					UasAccessLogPage.class, "test.js");

			@Override
			public void beforeRender(Component component) {
				//
				// component.getResponse().write(JavaScriptUtils.SCRIPT_OPEN_TAG);
				// component.getResponse().write("var node = Y.one('#myRunning');alert(node);");
				// component.getResponse().write(JavaScriptUtils.SCRIPT_CLOSE_TAG);
			}

			@Override
			public void renderHead(Component component, IHeaderResponse response) {
				// PageParameters pageParameters=new PageParameters();
				// pageParameters.add("date", "5/1/2010");
				// System.out.println(pageParameters.getValues("date"));
				// //response.renderOnDomReadyJavaScript("alert('hello')"); //on
				// Load
				// response.renderJavaScriptReference(demoJs,pageParameters,null,false,null);
				// //include js file
				// // response.renderJavaScript(demoJs.toString(), null);
				// //<script> tag
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
								+ "horizontalGridlines:true,verticalGridlines:true,render:\"#cbs_rfp_chart\"});");
				// pageResponse.write("});");
				pageResponse.write("});})();");
				// pageResponse
				// .write("  var myAxes = {financials:{keys:['miscellaneous', 'revenue', 'expenses'],"
				// +
				// "position:'right',type:'numeric',styles:{majorTicks:{display: 'none'}}},dateRange:{keys:['date'],position:'bottom',type:'category',styles:{majorTicks:{display: 'none'},label: {rotation:-25,margin:{top:5}}}}};//define the series var seriesCollection = [ {type:'column',xAxis:'dateRange',yAxis:'financials',xKey:'date',yKey:'miscellaneous',xDisplayName:'Date',yDisplayName:'Miscellaneous',styles: {border: {weight: 5,color: '#58006e'},over: {fill: {alpha: 0.7}}}},{type:'column',xAxis:'dateRange',yAxis:'financials',xKey:'date',yKey:'expenses',yDisplayName:'Expenses',styles: {marker:{fill: {color: '#e0ddd0' },border: {weight: 1,color: '#cbc8ba'},over: {fill: {alpha: 0.7}}}}},{type:'combo',xAxis:'dateRange',yAxis:'financials',xKey:'date',yKey:'revenue',xDisplayName:'Date',yDisplayName:'Deductions',line: {color: '#ff7200'},marker: {fill: {color: '#ff9f3b'},border: {color: '#ff7200',weight: 1},over: {width: 12,height: 12},width:9,height:9}}];//instantiate the chartvar myChart = new Y.Chart({dataProvider:myDataValues, axes:myAxes, seriesCollection:seriesCollection, horizontalGridlines: true,verticalGridlines: true,render:'#mychart'});});");
				pageResponse.write(JavaScriptUtils.SCRIPT_CLOSE_TAG);

//				response.renderOnDomReadyJavaScript("YUI().use('node', function (Y) {var node = Y.one('#myRunning');node.hide();})");
//				response.renderJavaScriptReference("YUI().use('node', function (Y) {var node = Y.one('#myRunning');node.hide();})");
//				response.renderJavaScriptReference("alert('aaa')");
				
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

	}

	public ArrayList<CBSAccessLog> getLists() {
		return lists;
	}

	public void setLists(ArrayList<CBSAccessLog> lists) {

		this.lists = lists;
	}
	
	class MyModel extends  AbstractReadOnlyModel{
		
		public MyModel(){
			super();
		}
		
		@Override
		public Object getObject() {
			if (ServiceAggregation.accessLogResults == null) {
				return null;
			} else {
				return ServiceAggregation.accessLogResults; 
			}
		}
		
	}

	

}
