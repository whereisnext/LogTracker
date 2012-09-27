package com.vance.logTracker.view.summary.base;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

public abstract class MYSelectModalWindow extends ModalWindow {
    public MYSelectModalWindow(String id) {
        super(id);

        // Set sizes of this ModalWindow. You can also do this from the HomePage
        // but its not a bad idea to set some good default values.
        setInitialWidth(300);
        setInitialHeight(300);

        setTitle("Summary Page");

        // Set the content panel, implementing the abstract methods
        setContent(new MYSelectContentPanel(this.getContentId()){
            void onCancel(AjaxRequestTarget target) {
                MYSelectModalWindow.this.onCancel(target);
            }

            void onSelect(AjaxRequestTarget target, String startDate,String endDate,String appName) {
            	MYSelectModalWindow.this.onSelect(target, startDate,endDate,appName);
            }
        });
    }

    abstract void onCancel(AjaxRequestTarget target);

    abstract void onSelect(AjaxRequestTarget target, String startDate,String endDate,String appName);

}
