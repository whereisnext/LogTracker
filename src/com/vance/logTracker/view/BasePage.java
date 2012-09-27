package com.vance.logTracker.view;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.JavaScriptPackageResource;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.time.Duration;

import com.mongodb.DB;
import com.vance.logTracker.application.LogTrackerApplication;
import com.vance.logTracker.application.ServiceAggregation;
import com.vance.logTracker.view.popup.SelectModalWindow;

public class BasePage extends WebPage{
	
	private static PageParameters params=initParams();
	
	private static Label label=null;
	
	
	private static Label chart=null;
	
	public BasePage(){
		super();
		
		makingLink();
	}
	
	private void makingLink() {
		add(new AjaxLink("cbs"){
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(CBSAggregationPage.class);
			}
		});
		
		add(new AjaxLink("sm"){
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(SMAggregationPage.class);
			}
		});
		
		
		add(new AjaxLink("nexus"){
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(NexusAggregationPage.class);
			}
			
		});
		
		add(new AjaxLink("ep"){
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(EPAggregationPage.class);
			}
		});
		
		add(new AjaxLink("am"){
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(AMAggregationPage.class);
			}
		});
		
		
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
			public void onCancel(AjaxRequestTarget target){
				
				close(target);
				System.out.println("Click Basepage Cancel!");
			}
			@Override
			public void onSelect(AjaxRequestTarget target, String selection){
				System.out.println("Click Select!");
				close(target);
			}

			@Override
			public void onSubmit(AjaxRequestTarget target) {
				System.out.println("Click Submit!");
				close(target);
			}

			@Override
			public void onClick(AjaxRequestTarget target) {
				System.out.println("Click Submit!");
				close(target);
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

	public BasePage(PageParameters pageParams) {
		super(params);
		makingLink();

	}
	
	
	public BasePage(final IModel<?> model){
		super(model);
	}

	private static PageParameters initParams() {
		PageParameters params=new PageParameters();
		params.add("query","vancezhao@gmail.com");
		return params;
	}

	public void renderHead(IHeaderResponse response) {
		response.renderCSSReference("main.css");
	  }
	
	
	@Override
	public void onBeforeRender(){
		super.onBeforeRender();
		
	}
	
}
