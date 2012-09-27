package com.vance.logTracker.application;

public class DataFileMapping {
	
	private static String dataUrlMapping="";
	private String CollectName;
	
	private String dataUrlMappingCollectionName;
	
	public DataFileMapping(String CollectName){
		this.setCollectName(CollectName);
		this.setDataUrlMappingCollectionName(dataUrlMapping);
	}

	public String getCollectName() {
		return CollectName;
	}

	public void setCollectName(String collectName) {
		CollectName = collectName;
	}

	public String getDataUrlMappingCollectionName() {
		return dataUrlMappingCollectionName;
	}

	public void setDataUrlMappingCollectionName(
			String dataUrlMappingCollectionName) {
		this.dataUrlMappingCollectionName = dataUrlMappingCollectionName;
	}
	
	

	
}
