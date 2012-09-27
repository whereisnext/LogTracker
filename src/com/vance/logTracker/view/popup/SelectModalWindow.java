package com.vance.logTracker.view.popup;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.CloseButtonCallback;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;

import com.vance.logTracker.persistence.factory.MySqlInstanceFactory;
import com.vance.logTracker.view.component.RfpSummaryPanel;
import com.vance.logTracker.view.summary.ServiceSummary;

public abstract class SelectModalWindow extends ModalWindow {
	
	

	
    public SelectModalWindow(String id) {
        super(id);

        // Set sizes of this ModalWindow. You can also do this from the HomePage
        // but its not a bad idea to set some good default values.
        setOutputMarkupId(true);
        setUseInitialHeight(true);
        setInitialWidth(1024);
        setInitialHeight(800);

        setTitle("Summary Page");
        


        // Set the content panel, implementing the abstract methods
        setContent(new SelectContentPanel(this.getContentId()){
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;


			public void onCancel(AjaxRequestTarget target) {
                SelectModalWindow.this.onCancel(target);
            }

            public void onSelect(AjaxRequestTarget target, String selection) {

                SelectModalWindow.this.onSelect(target, selection);
            }
            
            
            @Override
			public
			void onSumbit(AjaxRequestTarget target) {

            	SelectModalWindow.this.onSubmit(target);
			}

			@Override
			public void onClick(AjaxRequestTarget target) {
				SelectModalWindow.this.onClick(target);
			}
        });
    }

    public abstract void onCancel(AjaxRequestTarget target);

    public abstract void onSelect(AjaxRequestTarget target, String selection);
    
    public abstract void onSubmit(AjaxRequestTarget target);
    
    public abstract void onClick(AjaxRequestTarget target);
    
}
