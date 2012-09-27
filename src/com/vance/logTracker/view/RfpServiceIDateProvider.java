package com.vance.logTracker.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.vance.logTracker.domain.CBSAccessLog;

public class RfpServiceIDateProvider implements IDataProvider<Object> {
	
	private HashMap<String,? extends ArrayList> cbsAccessLog;
	
	public RfpServiceIDateProvider(HashMap<String, ? extends ArrayList> maps ){
		this.setCbsAccessLog(maps);
	}
	

	@Override
	public void detach() {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<? extends Object> iterator(int first, int count) {
		// TODO Auto-generated method stub
		return cbsAccessLog.get("weekFive").listIterator();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IModel<Object> model(Object object) {
		// TODO Auto-generated method stub
		System.out.println("getModel");
		return null; 
	}


	public HashMap<String,? extends ArrayList> getCbsAccessLog() {
		return cbsAccessLog;
	}


	public void setCbsAccessLog(HashMap<String,? extends ArrayList> cbsAccessLog) {
		this.cbsAccessLog = cbsAccessLog;
	}


	

}
