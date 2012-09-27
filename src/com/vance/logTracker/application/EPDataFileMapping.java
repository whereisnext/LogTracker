package com.vance.logTracker.application;

public class EPDataFileMapping extends DataFileMapping{
	
	private static String dataUrlMapping="EP_Access_Log_URLMapping";
	
	private String CollectName;

	private String dataUrlMappingCollectionName;
	
	public EPDataFileMapping(String collectName){
		super(collectName);
		this.setCollectName(collectName);
		this.setDataUrlMappingCollectionName(dataUrlMapping);
	}

	public String getCollectName() {
		return CollectName;
	}

	public void setCollectName(String collectName) {
		CollectName = collectName;
	}

	public static String getDataUrlMapping() {
		return dataUrlMapping;
	}

	public static void setDataUrlMapping(String dataUrlMapping) {
		EPDataFileMapping.dataUrlMapping = dataUrlMapping;
	}

	public String getDataUrlMappingCollectionName() {
		return dataUrlMappingCollectionName;
	}

	public void setDataUrlMappingCollectionName(
			String dataUrlMappingCollectionName) {
		this.dataUrlMappingCollectionName = dataUrlMappingCollectionName;
	}
}
