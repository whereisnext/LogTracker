package com.vance.logTracker.view.summary.base;

import org.apache.wicket.model.IModel;

public class SummaryPanelModel implements IModel{
	
	private IModel summaryPanelContainerModel;
	
	 private DateModelType type;

	  public enum DateModelType
	  {
	    startDate,endDate;
	  };
	
	public SummaryPanelModel(IModel SummaryPanelContainerModel,DateModelType type){
		this.summaryPanelContainerModel=SummaryPanelContainerModel;
		this.type=type;
	}
	
	
	@Override
	public void detach() {
		summaryPanelContainerModel.detach();
	}

	@Override
	public Object getObject() {
		Summary summary=(Summary)summaryPanelContainerModel.getObject();
		
		switch (type)
	    {

	      case startDate:

	        return summary.getStartDate();
	      case endDate:
	    	  return summary.getStartDate();
	    }

	    throw new UnsupportedOperationException("invalid DateType = "
	                                            + type.name());
	
		
		
	}

	@Override
	public void setObject(Object object) {
		Summary summary=(Summary)summaryPanelContainerModel.getObject();
		
		switch (type)
	    {

	      case startDate:

	    	  summary.setStartDate((String)object);
	      case endDate:
	    	  summary.setEndDate((String)object);
	    }

	    throw new UnsupportedOperationException("invalid DateType = "
	                                            + type.name());
	}
		
	

}
