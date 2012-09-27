package com.vance.logTracker.domain;

import java.util.Date;

public class CBSAccessLog extends AccessLog  {
	
	
	
	private String api="";
	
	private Double totalHits=0.00;
	
	private Double minResponseTime=0.00;
	
	private Double avgResponseTime=0.00;
	
	private Double maxResponseTime=0.00;
	
	private double ninetyPercentResp=-1;
	
	private String count_3000="";
	
	private String count_1000="";
	
	private String count_50="";
	
	private String count_2000="";
	
	private String count_500="";
	
	private String count_200="";
	
	private String count_0="";
	
	private  Date date;
	
	private Double totalResponseTime=0.00;
	
	private java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00"); 
	
	private Double avgResponseDataSize=0.00;
	
	private Double responseDataSize=0.00;
	
	private String logDateFlag="";
	
	private String service="";

	private String url="";
	/*
	 * If the http status code is not 200 and 304
	 * */
	private int ErrorCount;
	
	public CBSAccessLog(){
		
	}
	
	public String getCount_50() {
		return count_50;
	}

	public void setCount_50(String count_50) {
		this.count_50 = count_50;
	}

	public String getCount_2000() {
		return count_2000;
	}

	public void setCount_2000(String count_2000) {
		this.count_2000 = count_2000;
	}

	public String getCount_500() {
		return count_500;
	}

	public void setCount_500(String count_500) {
		this.count_500 = count_500;
	}

	public String getCount_200() {
		return count_200;
	}

	public void setCount_200(String count_200) {
		this.count_200 = count_200;
	}

	public String getCount_0() {
		return count_0;
	}

	public void setCount_0(String count_0) {
		this.count_0 = count_0;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCount_3000() {
		return count_3000;
	}



	public double getNinetyPercentResp() {
		return ninetyPercentResp;
	}

	public void setNinetyPercentResp(double ninetyPercentResp) {
		this.ninetyPercentResp = ninetyPercentResp;
	}

	public Double getAvgResponseTime() {
		return Double.valueOf(df.format(avgResponseTime));
	}

	public void setAvgResponseTime(Double avgResponseTime) {
		this.avgResponseTime = avgResponseTime;
	}

	public Double getMinResponseTime() {
		return Double.valueOf(df.format(minResponseTime));
	}

	public void setMinResponseTime(Double minResponseTime) {
		this.minResponseTime = minResponseTime;
	}

	public Double getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(Double totalHits) {
		this.totalHits = totalHits;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public Double getMaxResponseTime() {
		return Double.valueOf(df.format(maxResponseTime));
	}

	public void setMaxResponseTime(Double maxResponseTime) {
		this.maxResponseTime = maxResponseTime;
	}

	public String getCount3000() {
		return count_3000;
	}

	public void setCount_3000(String count_3000) {
		this.count_3000 = count_3000;
	}

	public Double getAvgResponseDataSize() {
		return Double.valueOf(df.format(avgResponseDataSize));
	}

	public void setAvgResponseDataSize(Double avgResponseDataSize) {
		this.avgResponseDataSize = avgResponseDataSize;
	}

	public String getCount_1000() {
		return count_1000;
	}

	public void setCount_1000(String count_1000) {
		this.count_1000 = count_1000;
	}

	public Double getTotalResponseTime() {
		return totalResponseTime;
	}

	public void setTotalResponseTime(Double totalResponseTime) {
		this.totalResponseTime = totalResponseTime;
	}

	public Double getResponseDataSize() {
		return responseDataSize;
	}

	public void setResponseDataSize(Double responseDataSize) {
		this.responseDataSize = responseDataSize;
	}

	public String getLogDateFlag() {
		return logDateFlag;
	}

	public void setLogDateFlag(String logDateFlag) {
		this.logDateFlag = logDateFlag;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getErrorCount() {
		return ErrorCount;
	}

	public void setErrorCount(int errorCount) {
		ErrorCount = errorCount;
	}

	


	
}
