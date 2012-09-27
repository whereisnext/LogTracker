package com.vance.logTracker.view.summary.base;

import java.io.Serializable;
import java.util.List;

import com.vance.logTracker.view.summary.ServiceSummary;

public class TestModel implements Serializable   {
	private String name;
	
	private  String lastestStartDate;
	
	private  String lastestEndDate;

	private String appName;
	
	private  String cbs;
	
	private  String smName;
	
	private  String nexusName;
	
	private  String epName;
	
	private  String amName;
	
	private  String message;
	
	private  List<ServiceSummary> serviceSummary;
	
	private  List<ServiceSummary> smSmmary;
	
	private  List<ServiceSummary> nexusSummary;
	
	private  List<ServiceSummary> epSummary;
	
	private  List<ServiceSummary> amSummary;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public  String getLastestStartDate() {
		return lastestStartDate;
	}

	public  void setLastestStartDate(String lastestStartDate) {
		this.lastestStartDate = lastestStartDate;
	}

	public  String getLastestEndDate() {
		return lastestEndDate;
	}

	public  void setLastestEndDate(String lastestEndDate) {
		this.lastestEndDate = lastestEndDate;
	}

	public  String getCbs() {
		return cbs;
	}

	public  void setCbs(String cbs) {
		this.cbs = cbs;
	}

	public  String getSmName() {
		return smName;
	}

	public  void setSmName(String smName) {
		this.smName = smName;
	}

	public  String getNexusName() {
		return nexusName;
	}

	public  void setNexusName(String nexusName) {
		this.nexusName = nexusName;
	}

	public  String getEpName() {
		return epName;
	}

	public  void setEpName(String epName) {
		this.epName = epName;
	}

	public  String getAmName() {
		return amName;
	}

	public  void setAmName(String amName) {
		this.amName = amName;
	}

	public  List<ServiceSummary> getServiceSummary() {
		return serviceSummary;
	}

	public void setServiceSummary(List<ServiceSummary> serviceSummary) {
		this.serviceSummary = serviceSummary;
	}

	public  String getMessage() {
		return message;
	}

	public  void setMessage(String message) {
		this.message = message;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public  List<ServiceSummary> getSmSmmary() {
		return smSmmary;
	}

	public  void setSmSmmary(List<ServiceSummary> smSmmary) {
		this.smSmmary = smSmmary;
	}

	public List<ServiceSummary> getNexusSummary() {
		return nexusSummary;
	}

	public void setNexusSummary(List<ServiceSummary> nexusSummary) {
		this.nexusSummary = nexusSummary;
	}

	public List<ServiceSummary> getEpSummary() {
		return epSummary;
	}

	public void setEpSummary(List<ServiceSummary> epSummary) {
		this.epSummary = epSummary;
	}

	public List<ServiceSummary> getAmSummary() {
		return amSummary;
	}

	public void setAmSummary(List<ServiceSummary> amSummary) {
		this.amSummary = amSummary;
	}
	
}
