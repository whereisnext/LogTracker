package com.vance.logTracker.util;

public enum DateByWeek {
//	weekOne("2011-10-03","2011-10-07"),
//	
//	weekTwo("2011-10-10","2011-10-14"),
//	
//	weekThree("2011-10-17","2011-10-22"),
//	
//	weekFour("2011-10-24","2011-10-29"),
//	
//	weekFive("2011-10-31","2011-11-05");
	
	
	CBS_ACCESS_LOGS_20110824("2011-08-24","2011-08-25");
//	Sep09("2011-09-09","2011-09-10"),
//	Sep12("2011-09-12","2011-09-13"),
//	Oct28("2011-10-28","2011-10-29"),
//	Oct31("2011-10-31","2011-11-01"),
//	Nov03("2011-11-03","2011-11-04");
//	beforeAuguestRelease("2011-08-24","2011-08-25"),
//	afterAuguestRelease("2011-09-07","2011-09-08"),
	
//	beforeSep10crash("2011-09-07","2011-09-08"),
//	afterSep10crash("2011-09-14","2011-09-15"),?will use beforeSep17offcieMove
//	afterSep10crash("2011-09-14","2011-09-15");
//	beforeSep17offcieMove("2011-09-16","2011-09-17"),
//	afterSep17offcieMove("2011-09-19","2011-09-20"),
//	
//	beforeSepRelease("2011-09-19","2011-09-20"),
//	afterSepRelease("2011-09-22","2011-09-23");
//	
	private String weekStartDate;
	private String weekEndDate;
	
	
	DateByWeek(String weekStartDate,String weekEndDate){
		this.setWeekStartDate(weekStartDate);
		this.setWeekEndDate(weekEndDate);
		
		
	}

	public String getWeekStartDate() {
		return weekStartDate;
	}

	public void setWeekStartDate(String weekStartDate) {
		this.weekStartDate = weekStartDate;
	}

	public String getWeekEndDate() {
		return weekEndDate;
	}

	public void setWeekEndDate(String weekEndDate) {
		this.weekEndDate = weekEndDate;
	}
	
}
