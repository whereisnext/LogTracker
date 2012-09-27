package com.vance.logTracker.view.summary.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.model.ComponentModel;
import org.apache.wicket.model.ComponentPropertyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.vance.logTracker.persistence.factory.MySqlInstanceFactory;
import com.vance.logTracker.view.CBSAggregationPage;
import com.vance.logTracker.view.component.RfpSummaryPanel;
import com.vance.logTracker.view.summary.ServiceSummary;
import com.vance.logTracker.view.summary.SummaryPanel;

/**
 * Homepage
 */
public class HomePage extends WebPage {
	
	public Connection conn=MySqlInstanceFactory.getInstance();

	public HashMap<String,String> summaryResultMap=new HashMap<String,String>();

	private static String defaultQuery="select createdDate from CBS order by createdDate desc limit 1";
	
	
	private static String lastestStartDate;
	
	private static String lastestEndDate;

    public HomePage(final PageParameters parameters) {
    	
    	lastestStartDate=parameters.get("startDate").toString();
    	lastestEndDate=parameters.get("endDate").toString();
        // The label that shows the result from the ModalWindow
        final Label resultLabel = new Label("resultlabel", new Model(""));
        resultLabel.setOutputMarkupId(true);

        TestModel cbsModel=new TestModel();
       
        cbsModel.setName("CBS");
        cbsModel.setSmName("SM");

        cbsModel.setNexusName("Nexus");
        cbsModel.setEpName("EP");
        
		final Form myForm=new Form("testForm",new CompoundPropertyModel(cbsModel));
		myForm.setOutputMarkupId(true);
		final Label label=new Label("name",new PropertyModel(cbsModel,"name"));
		
		label.setOutputMarkupId(true);
		myForm.add(label);
		
		if(lastestStartDate==null&&lastestEndDate==null){
			try {
				ResultSet rs=conn.createStatement().executeQuery("select createdDate from cbs order by createdDate desc limit 1");
				if(rs.next()){
					lastestStartDate=lastestEndDate=rs.getString("createdDate");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		System.out.println("lastestStartDate is: "+lastestStartDate);
		System.out.println("lastestEndDate is: "+lastestEndDate);
		
//		setLastestStartDate("");
//		
//		setLastestEndDate("");
		
		 resultLabel.setDefaultModelObject("Start Date: "+lastestStartDate+""+"\n"+"End Date: "+lastestEndDate).setEscapeModelStrings(true);
         
		List<ServiceSummary> cbslists=getCBSServcieSummary("CBS",lastestStartDate,lastestEndDate);	
		
		List<ServiceSummary> smLists=getApplicationSummary("SM",lastestStartDate,lastestEndDate);	
		
		List<ServiceSummary> nexusLists=getApplicationSummary("Nexus",lastestStartDate,lastestEndDate);	
		
		
		
	

		cbsModel.setServiceSummary(cbslists);
		final Label serviceLabel=new Label("applicationName",new PropertyModel(cbsModel,"cbs"));
		serviceLabel.setOutputMarkupId(true);
		myForm.add(serviceLabel);

		
		final ListView cbslistView = new ListView("serviceSummary",cbslists){
			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem item) {
				ServiceSummary serviceSummary=(ServiceSummary) item.getModelObject();
				
				Label serviceNameLabel=new Label("servcieName",new PropertyModel(item.getModel(), "name"));
				serviceNameLabel.setOutputMarkupId(true);
				item.add(serviceNameLabel);
				
				Label avgRespTime=new Label("avgResp",new PropertyModel(item.getModel(), "avgRespTime"));
				avgRespTime.setOutputMarkupId(true);
				item.add(avgRespTime);
				
				Label totalHitsLabel=new Label("totalHits",new PropertyModel(item.getModel(), "totalHits"));
				totalHitsLabel.setOutputMarkupId(true);
				
				item.add(totalHitsLabel);
				
				Label count3000Label=new Label("count_3000",new PropertyModel(item.getModel(), "count_3000Percent"));
				count3000Label.setOutputMarkupId(true);
				item.add(count3000Label);
				
				Label count2000Label=new Label("count_2000",new PropertyModel(item.getModel(), "count_2000Percent"));
				count2000Label.setOutputMarkupId(true);
				item.add(count2000Label);
				
				
				Label count1000Label=new Label("count_1000",new PropertyModel(item.getModel(), "count_1000Percent"));
				count1000Label.setOutputMarkupId(true);
				item.add(count1000Label);
				
				Label count500Label=new Label("count_500",new PropertyModel(item.getModel(), "count_500Percent"));
				count500Label.setOutputMarkupId(true);
				
				
				item.add(count500Label);
//				
				Label count0Label=new Label("count_0",new PropertyModel(item.getModel(), "count_0Precent"));
				count0Label.setOutputMarkupId(true);
				item.add(count0Label);
				
			}
		};	
		
		final Label smNameLabel=new Label("smName",new PropertyModel(cbsModel,"smName"));
		smNameLabel.setOutputMarkupId(true);
		myForm.add(smNameLabel);
		
		final ListView smListView = new ListView("smSummary",smLists){
			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem item) {
				ServiceSummary serviceSummary=(ServiceSummary) item.getModelObject();
				
//				Label serviceNameLabel=new Label("servcieName",new PropertyModel(item.getModel(), "name"));
//				serviceNameLabel.setOutputMarkupId(true);
//				item.add(serviceNameLabel);
				
				Label avgRespTime=new Label("avgResp",new PropertyModel(item.getModel(), "avgRespTime"));
				avgRespTime.setOutputMarkupId(true);
				item.add(avgRespTime);
				
				Label totalHitsLabel=new Label("totalHits",new PropertyModel(item.getModel(), "totalHits"));
				totalHitsLabel.setOutputMarkupId(true);
				
				item.add(totalHitsLabel);
				
				Label count3000Label=new Label("count_3000",new PropertyModel(item.getModel(), "count_3000Percent"));
				count3000Label.setOutputMarkupId(true);
				item.add(count3000Label);
				
				Label count2000Label=new Label("count_2000",new PropertyModel(item.getModel(), "count_2000Percent"));
				count2000Label.setOutputMarkupId(true);
				item.add(count2000Label);
				
				
				Label count1000Label=new Label("count_1000",new PropertyModel(item.getModel(), "count_1000Percent"));
				count1000Label.setOutputMarkupId(true);
				item.add(count1000Label);
				
				Label count500Label=new Label("count_500",new PropertyModel(item.getModel(), "count_500Percent"));
				count500Label.setOutputMarkupId(true);
				
				
				item.add(count500Label);
				
				Label count0Label=new Label("count_0",new PropertyModel(item.getModel(), "count_0Precent"));
				count0Label.setOutputMarkupId(true);
				item.add(count0Label);
				
			}
		};	
		
		
		
		cbsModel.setNexusSummary(nexusLists);
		final Label nexusNameLabel=new Label("nexusName",new PropertyModel(cbsModel,"nexusName"));
		nexusNameLabel.setOutputMarkupId(true);
		myForm.add(nexusNameLabel);
		
		
		final ListView nexusListView = new ListView("nexusSummary",nexusLists){
			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem item) {
				ServiceSummary serviceSummary=(ServiceSummary) item.getModelObject();
				
//				Label serviceNameLabel=new Label("servcieName",new PropertyModel(item.getModel(), "name"));
//				serviceNameLabel.setOutputMarkupId(true);
//				item.add(serviceNameLabel);
				
				Label avgRespTime=new Label("avgResp",new PropertyModel(item.getModel(), "avgRespTime"));
				avgRespTime.setOutputMarkupId(true);
				item.add(avgRespTime);
				
				Label totalHitsLabel=new Label("totalHits",new PropertyModel(item.getModel(), "totalHits"));
				totalHitsLabel.setOutputMarkupId(true);
				
				item.add(totalHitsLabel);
				
				Label count3000Label=new Label("count_3000",new PropertyModel(item.getModel(), "count_3000Percent"));
				count3000Label.setOutputMarkupId(true);
				item.add(count3000Label);
				
				Label count2000Label=new Label("count_2000",new PropertyModel(item.getModel(), "count_2000Percent"));
				count2000Label.setOutputMarkupId(true);
				item.add(count2000Label);
				
				
				Label count1000Label=new Label("count_1000",new PropertyModel(item.getModel(), "count_1000Percent"));
				count1000Label.setOutputMarkupId(true);
				item.add(count1000Label);
				
				Label count500Label=new Label("count_500",new PropertyModel(item.getModel(), "count_500Percent"));
				count500Label.setOutputMarkupId(true);
				
				
				item.add(count500Label);
				
				Label count0Label=new Label("count_0",new PropertyModel(item.getModel(), "count_0Precent"));
				count0Label.setOutputMarkupId(true);
				item.add(count0Label);
				
			}
		};	
		
		
		
		List<ServiceSummary> epLists=getApplicationSummary("ep", lastestStartDate, lastestEndDate);
		cbsModel.setEpSummary(epLists);
		final Label epNameLabel=new Label("epName",new PropertyModel(cbsModel,"epName"));
		epNameLabel.setOutputMarkupId(true);
		myForm.add(epNameLabel);
		
		
		final ListView epListView = new ListView("epSummary",epLists){
			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem item) {
				ServiceSummary serviceSummary=(ServiceSummary) item.getModelObject();
				
//				Label serviceNameLabel=new Label("servcieName",new PropertyModel(item.getModel(), "name"));
//				serviceNameLabel.setOutputMarkupId(true);
//				item.add(serviceNameLabel);
				
				Label avgRespTime=new Label("avgResp",new PropertyModel(item.getModel(), "avgRespTime"));
				avgRespTime.setOutputMarkupId(true);
				item.add(avgRespTime);
				
				Label totalHitsLabel=new Label("totalHits",new PropertyModel(item.getModel(), "totalHits"));
				totalHitsLabel.setOutputMarkupId(true);
				
				item.add(totalHitsLabel);
				
				Label count3000Label=new Label("count_3000",new PropertyModel(item.getModel(), "count_3000Percent"));
				count3000Label.setOutputMarkupId(true);
				item.add(count3000Label);
				
				Label count2000Label=new Label("count_2000",new PropertyModel(item.getModel(), "count_2000Percent"));
				count2000Label.setOutputMarkupId(true);
				item.add(count2000Label);
				
				
				Label count1000Label=new Label("count_1000",new PropertyModel(item.getModel(), "count_1000Percent"));
				count1000Label.setOutputMarkupId(true);
				item.add(count1000Label);
				
				Label count500Label=new Label("count_500",new PropertyModel(item.getModel(), "count_500Percent"));
				count500Label.setOutputMarkupId(true);
				
				
				item.add(count500Label);
				
				Label count0Label=new Label("count_0",new PropertyModel(item.getModel(), "count_0Precent"));
				count0Label.setOutputMarkupId(true);
				item.add(count0Label);
				
			}
		};	
		
		
		
		
		
		
		nexusListView.setOutputMarkupId(true);
		
		smListView.setOutputMarkupId(true);
		
		epListView.setOutputMarkupId(true);
		
//		listContainer.add(listView);
		cbslistView.setOutputMarkupId(true);
		
		myForm.add(cbslistView);
		
		myForm.add(smListView);
		
		myForm.add(nexusListView);
		
		myForm.add(epListView);
		
		
		add(myForm);
		
        add(resultLabel);



        // The ModalWindow, showing some choices for the user to select.
        final ModalWindow selectModalWindow = new MYSelectModalWindow("modalwindow"){

            void onSelect(AjaxRequestTarget target, String startDate,String endDate,String appName) {
                // Handle Select action
                resultLabel.setDefaultModelObject("Start Date: "+startDate+""+"\n"+"End Date: "+endDate).setEscapeModelStrings(true);
                TestModel testModel=new TestModel();
                testModel.setName("");
                testModel.setAppName(appName);
                testModel.setAmName("AM");
                testModel.setCbs("CBS");
                testModel.setEpName("EP");
                testModel.setNexusName("Nexus");
                testModel.setLastestStartDate(startDate);
                testModel.setLastestEndDate(endDate);
                
                List<ServiceSummary> serviceSummaryLists;
                if(appName.contains("CBS")){
                	serviceSummaryLists=getCBSServcieSummary(appName,startDate,endDate);	
                }else{
                	serviceSummaryLists=getApplicationSummary(appName, startDate, endDate);
                }
                
                List<ServiceSummary> smSummaryLists=getApplicationSummary("sm", startDate, endDate);

                List<ServiceSummary> nexusSummaryLists=getApplicationSummary("nexus", startDate, endDate);
                
                List<ServiceSummary> epSummaryLists=getApplicationSummary("ep", startDate, endDate);

                
                testModel.setServiceSummary(serviceSummaryLists);
               
                testModel.setSmSmmary(smSummaryLists);
                
                testModel.setNexusSummary(nexusSummaryLists);
                
                testModel.setEpSummary(epSummaryLists);
                
                
                serviceLabel.setDefaultModelObject(testModel.getCbs());
                smNameLabel.setDefaultModelObject(testModel.getSmName());
                cbslistView.setDefaultModelObject(testModel.getServiceSummary());
                smListView.setDefaultModelObject(testModel.getSmSmmary());
                nexusListView.setDefaultModelObject(testModel.getNexusSummary());
                epListView.setDefaultModelObject(testModel.getEpSummary());
                
//                System.out.println(testModel.getServiceSummary().get(0).getAvgRespTime());
//                listContainer.setDefaultModelObject(testModel.getServiceSummary());
//                cbslistView.setList(serviceSummaryLists);
//                label.setDefaultModelObject(testModel.getName());
                target.add(resultLabel);
//                target.add(label);
//                target.add(serviceLabel);
                target.addChildren(cbslistView, Label.class);
                
                target.addChildren(smListView, Label.class);

                target.addChildren(nexusListView, Label.class);

                target.addChildren(epListView, Label.class);

                close(target);
            }

            void onCancel(AjaxRequestTarget target) {
                // Handle Cancel action
                resultLabel.setDefaultModelObject("ModalWindow cancelled.");
                target.add(resultLabel);
                close(target);
            }

        };
        
        add(selectModalWindow);
        
        add(new AjaxLink("homePage"){
            public void onClick(AjaxRequestTarget target) {
            	
                setResponsePage(CBSAggregationPage.class);
            }
        });
        

        // The AjaxLink to open the modal window
        add(new AjaxLink("linkToModalWindow"){
            public void onClick(AjaxRequestTarget target) {
            	
                selectModalWindow.show(target);
            }
        });
    }
    
    
    private List<ServiceSummary> getCBSServcieSummary(String dbName,String startDate,String endDate) {
    	
		List<ServiceSummary> listSummary=new ArrayList<ServiceSummary>();
		String[] servcieNames={"rfpService","contactService","vendorService","useraccountService"};
		for(String serviceName:servcieNames){
			ServiceSummary serviceSummary=new ServiceSummary();
			String query="select sum(totalHits) as 'TotalHits',sum(totalHits*avgResponseTime)/sum(totalHits) as 'AvgResponseTime', " +
					"sum(count_3000)/sum(totalHits)*100 as 'count_3000Percent'," +
					"sum(count_2000)/sum(totalHits)*100 as 'count_2000Percent'," +
					"sum(count_1000)/sum(totalHits)*100 as 'count_1000Percent'," +
					"sum(count_500)/sum(totalHits)*100 as 'count_500Percent'," +
					"sum(count_50)/sum(totalHits)*100 as 'count_50Percent'," +
					"sum(count_0)/sum(totalHits)*100 as 'count_0Percent' from "+dbName+" where serviceName= '"+ serviceName+ "' " +
							"and createdDate between '"+ startDate+ "' and '"+ endDate+"'";
			System.out.println(query);
			ResultSet rs = null;
			try {
				rs = conn.createStatement().executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(rs.next()){
					serviceSummary.setAvgRespTime(rs.getString("AvgResponseTime"));
					serviceSummary.setTotalHits(rs.getString("TotalHits"));
					serviceSummary.setCount_0Precent(rs.getString("count_0Percent"));
					serviceSummary.setCount_1000Percent(rs.getString("count_1000Percent"));
					serviceSummary.setCount_3000Percent(rs.getString("count_3000Percent"));
					serviceSummary.setCount_2000Percent(rs.getString("count_2000Percent"));
					serviceSummary.setCount_500Percent(rs.getString("count_500Percent"));
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
	
	
	
	private List<ServiceSummary> getApplicationSummary(String dbName, String lastestStartDate2, String lastestEndDate2) {
		List<ServiceSummary> listSummary=new ArrayList<ServiceSummary>();
		
//		for(String serviceName:servcieNames){
			ServiceSummary serviceSummary=new ServiceSummary();
			String query="select sum(totalHits) as 'TotalHits',sum(totalHits*avgResponseTime)/sum(totalHits) as 'AvgResponseTime', " +
					"sum(count_3000)/sum(totalHits)*100 as 'count_3000Percent'," +
					"sum(count_2000)/sum(totalHits)*100 as 'count_2000Percent'," +
					"sum(count_1000)/sum(totalHits)*100 as 'count_1000Percent'," +
					"sum(count_500)/sum(totalHits)*100 as 'count_500Percent'," +
					"sum(count_50)/sum(totalHits)*100 as 'count_50Percent'," +
					"sum(count_0)/sum(totalHits)*100 as 'count_0Percent' from "+dbName+" where createdDate between '"+ lastestStartDate2+ "' and '"+ lastestEndDate2+"'";
			System.out.println(query);
			ResultSet rs = null;
			try {
				rs = conn.createStatement().executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(rs.next()){
					serviceSummary.setAvgRespTime(rs.getString("AvgResponseTime"));
					serviceSummary.setTotalHits(rs.getString("TotalHits"));
					serviceSummary.setCount_0Precent(rs.getString("count_0Percent"));
					serviceSummary.setCount_1000Percent(rs.getString("count_1000Percent"));
					serviceSummary.setCount_3000Percent(rs.getString("count_3000Percent"));
					serviceSummary.setCount_2000Percent(rs.getString("count_2000Percent"));
					serviceSummary.setCount_500Percent(rs.getString("count_500Percent"));
					serviceSummary.setName("N/A");
					listSummary.add(serviceSummary);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return listSummary;
		
	}

	public void setSummaryResultMap(HashMap<String,String> summaryResultMap,String key,String value){
		summaryResultMap.put(key, value);
	}
	
	public HashMap<String,String> getSummaryResultMap(){
		return this.summaryResultMap;
	}



	public  String getLastestStartDate() {
		return lastestStartDate;
	}



	public  void setLastestStartDate(String lastestStartDate) {
		this.lastestStartDate = lastestStartDate;
	}



	public  String getLastestEndDate() {
		return lastestEndDate;
	}



	public  void setLastestEndDate(String lastestEndDate) {
		this.lastestEndDate = lastestEndDate;
	}
	
	public void renderHead(IHeaderResponse response) {
		response.renderCSSReference("main.css");
	  }
	
}