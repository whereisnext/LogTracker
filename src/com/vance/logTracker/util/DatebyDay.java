package com.vance.logTracker.util;

public enum DatebyDay {
	dayOne("2011-10-23","2011-10-24"),
	dayTwo("2011-10-24","2011-10-25"),
	dayThree("2011-10-25","2011-10-26"),
	dayFour("2011-10-26","2011-10-24"),
	dayFive("2011-10-27","2011-10-24"),
	daySix("2011-10-28","2011-10-24"),
	daySeven("2011-10-29","2011-10-30");
	
	private String startDate;
	private String endDate;
	DatebyDay(String startDate,String endDate){
		this.setRecordDate(startDate);
		this.endDate=endDate;
	}
	public String getRecordDate() {
		return startDate;
	}
	public void setRecordDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	
}
