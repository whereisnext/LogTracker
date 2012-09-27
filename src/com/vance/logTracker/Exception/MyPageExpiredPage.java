package com.vance.logTracker.Exception;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;

import com.vance.logTracker.view.BasePage;
import com.vance.logTracker.view.CBSAggregationPage;

public class MyPageExpiredPage extends BasePage {
	
	public MyPageExpiredPage(){
		add(new Label("errorCode","Page Expired!"));
		add(new AjaxLink("home"){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(CBSAggregationPage.class);
			}
			
		});
	}
}
