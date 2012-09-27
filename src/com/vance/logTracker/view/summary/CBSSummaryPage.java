package com.vance.logTracker.view.summary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.vance.logTracker.persistence.factory.MySqlInstanceFactory;
import com.vance.logTracker.view.BasePage;
import com.vance.logTracker.view.CBSAggregationPage;
import com.vance.logTracker.view.component.RfpSummaryPanel;
import com.vance.logTracker.view.popup.SelectModalWindow;

public class CBSSummaryPage extends BasePage {
	
	public Connection conn=MySqlInstanceFactory.getInstance();
	
	public HashMap<String,String> summaryResultMap=new HashMap<String,String>();
	
	private static final long serialVersionUID = 1L;
	
	private static String lastestStartDate;
	
	private static String lastestEndDate;
	public CBSSummaryPage(){
		
		popupMakeup();
//		
//		ResultSet rs;
//		try {
//			rs = conn.createStatement().executeQuery("select createdDate from CBS order by createdDate desc limit 1");
//			if(rs.next()){
//			lastestStartDate=rs.getString("createdDate");
//			lastestEndDate=rs.getString("createdDate");
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		
//		List<ServiceSummary> cbslists=getCBSServcieSummary("CBS");
//		
//		add(new Label("applicationName","Common Business Service"));
//		add(new RfpSummaryPanel("rfpSummaryPanel",cbslists));
//		
//		List<ServiceSummary> smlists=getApplicationSummary("SM");
//
//
//		add(new Label("smName","Supply Management"));
//		add(new RfpSummaryPanel("smSummaryPanel",smlists));
//		
//		List<ServiceSummary> nexuslists=getApplicationSummary("nexus");
//
//		
//		add(new Label("nexusName","NEXUS"));
//		add(new RfpSummaryPanel("nexusSummaryPanel",nexuslists));
//		
//		List<ServiceSummary> epLists=getApplicationSummary("ep");
//
//		
//		add(new Label("epName","Common Application"));
//		add(new RfpSummaryPanel("epSummaryPanel",epLists));
//		
//		List<ServiceSummary> amLists=getApplicationSummary("am");
//
//		
//		add(new Label("amName","Attendee Management"));
//		add(new RfpSummaryPanel("amSummaryPanel",amLists));
	}
	
	private void popupMakeup() {

        // The label that shows the result from the ModalWindow
        final Label resultLabel = new Label("resultlabel", new Model(""));
        resultLabel.setOutputMarkupId(true);
        add(resultLabel);



        // The ModalWindow, showing some choices for the user to select.
        final ModalWindow selectModalWindow = new SelectModalWindow("modalwindow"){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onCancel(AjaxRequestTarget target) {
				
			}

			@Override
			public void onSelect(AjaxRequestTarget target, String selection) {
				
			}

			@Override
			public void onSubmit(AjaxRequestTarget target) {
				
			}

			

			@Override
			public void onClick(AjaxRequestTarget target) {
				// TODO Auto-generated method stub
				
			}
        	
        };
        
        add(selectModalWindow);



        // The AjaxLink to open the modal window
        add(new AjaxLink("linkToModalWindow"){
            public void onClick(AjaxRequestTarget target) {
                selectModalWindow.show(target);
            }
        });
	}

	
	
	
	@Override
	public void renderHead(IHeaderResponse response){
		
	}
	
}
