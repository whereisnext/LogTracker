package com.vance.logTracker.view.component;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import com.vance.logTracker.view.summary.ServiceSummary;
import com.vance.logTracker.view.summary.base.TestModel;

public class RfpSummaryPanel extends Panel {

	
	
	public RfpSummaryPanel(String id,List<ServiceSummary> list) {
		super(id);
		super.setMarkupId(id);
		super.setOutputMarkupId(true);
		
		ListView listView = new ListView("serviceSummary",list ) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem item) {
				ServiceSummary serviceSummary=(ServiceSummary) item.getModelObject();
				item.add(new Label("servcieName",serviceSummary.getName()));
				item.add(new Label("avgResp",serviceSummary.getAvgRespTime()));
				item.add(new Label("totalHits",serviceSummary.getTotalHits()));
				item.add(new Label("count_3000",serviceSummary.getCount_3000Percent()+"%"));
				item.add(new Label("count_2000",serviceSummary.getCount_2000Percent()+"%"));
				item.add(new Label("count_1000",serviceSummary.getCount_1000Percent()+"%"));
				item.add(new Label("count_500",serviceSummary.getCount_500Percent()+"%"));
				item.add(new Label("count_0",serviceSummary.getCount_0Precent()+"%"));
				
			}
		};	
		
		add(listView);
	}


	public RfpSummaryPanel(String id, PropertyModel propertyModel) {
		super(id,propertyModel);
		super.setMarkupId(id);
		super.setOutputMarkupId(true);
		List<ServiceSummary> lists=(List<ServiceSummary>) new PropertyModel(propertyModel,"name");
		ListView listView = new ListView("serviceSummary",lists) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void populateItem(ListItem item) {
				ServiceSummary serviceSummary=(ServiceSummary) item.getModelObject();
				item.add(new Label("servcieName",serviceSummary.getName()));
				item.add(new Label("avgResp",serviceSummary.getAvgRespTime()));
				item.add(new Label("totalHits",serviceSummary.getTotalHits()));
				item.add(new Label("count_3000",serviceSummary.getCount_3000Percent()+"%"));
				item.add(new Label("count_2000",serviceSummary.getCount_2000Percent()+"%"));
				item.add(new Label("count_1000",serviceSummary.getCount_1000Percent()+"%"));
				item.add(new Label("count_500",serviceSummary.getCount_500Percent()+"%"));
				item.add(new Label("count_0",serviceSummary.getCount_0Precent()+"%"));
				
			}
		};	
		
		add(listView);
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
}
