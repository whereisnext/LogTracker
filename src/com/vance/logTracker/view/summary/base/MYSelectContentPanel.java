package com.vance.logTracker.view.summary.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.IClusterable;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

public abstract class MYSelectContentPanel extends Panel {

	
	private  String startDate;
	
	private String endDate;
	
	private String message;
	
	private final List lists=Arrays.asList(new String[]{"CBS","SM","EP","AM","Nexus"});
	
	private String selected="1";
	
    public MYSelectContentPanel(String id) {
        super(id);

        // Create the form, to use later for the buttons
        Form form = new Form("form");
        
        
        PropertyModel<String> messageStartDateModel = new PropertyModel<String>(this, "startDate");
        PropertyModel<String> messageEndDateModel = new PropertyModel<String>(this, "endDate");
        

		// The label displays the currently set message
        final TextField<String> startDateField=new TextField<String>("startDate",messageStartDateModel);
        final TextField<String> endDateField=new TextField<String>("endDate", messageEndDateModel);
		
		form.add(startDateField);
		form.add(endDateField);

		
//		final DropDownChoice choice=new DropDownChoice("dropdown",new PropertyModel(this,"selected"),lists);
//		
//		form.add(choice.setVisible(false));

        // Add some example 'selection' methods, to show as example
        // You can also use a full fledged form, I left that as an
        // exercise for the reader :-)
       

        form.add(new AjaxButton("selectionButton"){
        	/**
			 * 
			 */
			private static final long serialVersionUID = 1L;



			@Override
            protected void onSubmit(AjaxRequestTarget target, Form form) {
        		String startDate=startDateField.getModelObject().toString();
        		String endDate=endDateField.getModelObject().toString();
        		System.out.println(startDate+" &"+ endDate);
        		String choiceValue = null;
//        		if(choice.getDefaultModelObject().toString()==null){
//        			 choiceValue="CBS";
//        		}
        		onSelect(target,startDate,endDate,"CBS");
        		
            }

		

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				// TODO Auto-generated method stub
				
			}
        });
        
        


//        // Add a cancel / close button.
//        form.add(new AjaxButton("close") {
//        	@Override
//            public void onSubmit(AjaxRequestTarget target, Form form) {
//                onCancel(target);
//            }
//
//		
//
//			@Override
//			protected void onError(AjaxRequestTarget target, Form<?> form) {
//				// TODO Auto-generated method stub
//				
//			}
//        });
        
        add(form);

    }

    abstract void onCancel(AjaxRequestTarget target);

    abstract void onSelect(AjaxRequestTarget target, String startDate,String endDate,String appName);

	public  String getStartDate() {
		return startDate;
	}

	public  void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public List getLists() {
		return lists;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	

}



