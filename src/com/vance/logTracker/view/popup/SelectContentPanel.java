package com.vance.logTracker.view.popup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.vance.logTracker.persistence.factory.MySqlInstanceFactory;
import com.vance.logTracker.view.CBSAggregationPage;
import com.vance.logTracker.view.component.RfpSummaryPanel;
import com.vance.logTracker.view.summary.ServiceSummary;
import com.vance.logTracker.view.summary.SummaryPanel;
import com.vance.logTracker.view.summary.base.HomePage;

public abstract class SelectContentPanel extends Panel {
	
	public Connection conn=MySqlInstanceFactory.getInstance();
	
	public HashMap<String,String> summaryResultMap=new HashMap<String,String>();

	private static String defaultQuery="select createdDate from CBS order by createdDate desc limit 1";
	
	private static final long serialVersionUID = 1L;
	
	private  String lastestStartDate;
	
	private  String lastestEndDate;
	
	
    public SelectContentPanel(String id) {
        super(id);
        // Create the form, to use later for the buttons
        Form form = getForm("form",null,null);
        form.setOutputMarkupId(true);
        
        PropertyModel<String> messageStartDateModel = new PropertyModel<String>(
				this, "lastestStartDate");
		PropertyModel<String> messageEndDateModel = new PropertyModel<String>(
				this, "lastestStartDate");

		final TextField<String> startDateField = new TextField<String>(
				"startDate", messageStartDateModel);
		final TextField<String> endDateField = new TextField<String>("endDate",
				messageEndDateModel);

		form.add(startDateField);
		form.add(endDateField);
        
        form.add(new AjaxButton("gotoSummaryReport"){

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				
			}



			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				String startDateModelObject=startDateField.getInput();
				String endDateModelObject=endDateField.getInput();
				
				onCancel(target);
				PageParameters parameters=new PageParameters();
				
				parameters.add("startDate", startDateModelObject);
				parameters.add("endDate",endDateModelObject);
				System.out.println("startDate: "+startDateModelObject);
				System.out.println("endDate: "+endDateModelObject);
				
				setResponsePage(HomePage.class, parameters);
				
				
			}
			
		}).setOutputMarkupId(true);
         final SummaryPanel summaryPanel=new SummaryPanel("summaryPanel",this.getLastestStartDate(),this.getLastestEndDate());
         summaryPanel.setOutputMarkupId(true);
        form.add(summaryPanel);
        form.setOutputMarkupId(true);
        
        
        
        form.add(new Label("lastestStartDate",new PropertyModel(this,"lastestStartDate")));
        form.add(new Label("lastestEndDate",new PropertyModel(this,"lastestEndDate")));
        
       
        
//        AjaxFormSubmitBehavior formSubmitBehavior=new AjaxFormSubmitBehavior(form,"onSubmit"){
//        	
//			@Override
//			protected void onSubmit(AjaxRequestTarget target) {
//				target.add(new SummaryPanel("summaryPanel","2012-02-01","2012-02-02").setOutputMarkupId(true));
//			}
//
//			@Override
//			protected void onError(AjaxRequestTarget target) {
//				
//			}
//        	
//        };
//        
        
        add(form);

        final Label label=new Label("testHere","testHere");
        label.setOutputMarkupId(true);
        
//        add(new AjaxButton("summryResultByWeek"){
//
//			@Override
//			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
//				add(label);
//			}
//
//			@Override
//			protected void onError(AjaxRequestTarget target, Form<?> form) {
//				// TODO Auto-generated method stub
//				
//			}
//        	
//        });

        Button closeButton=new AjaxButton("close") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				
				onCancel(target);
				
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				
			}
        };
        
        

    }

    private Form getForm(String formName,String startDate,String endDate) {
    	ResultSet rs;
    	String query;
    	if(startDate==null){
    		query=defaultQuery;
    		try {
    			rs = conn.createStatement().executeQuery(query);
    			if(rs.next()){
    				setLastestStartDate(rs.getString("createdDate"));
    				setLastestEndDate(rs.getString("createdDate"));
    			}		
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		
    	}else{
    		setLastestStartDate(startDate);
    		setLastestEndDate(endDate);
    	}
    	
    	
    	Form form=new Form(formName,new CompoundPropertyModel(this));
    	form.setOutputMarkupId(true);
    	
   		return form;
	}

	public abstract void onCancel(AjaxRequestTarget target);

    public abstract void onSelect(AjaxRequestTarget target, String selection);

    public abstract void onSumbit(AjaxRequestTarget target);
    
    public abstract void onClick(AjaxRequestTarget target);

    
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

    
    
   
	
    
}
