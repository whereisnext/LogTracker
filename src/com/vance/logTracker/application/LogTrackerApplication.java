package com.vance.logTracker.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.settings.IResourceSettings;
import org.apache.wicket.util.resource.locator.IResourceStreamLocator;
import org.apache.wicket.util.resource.locator.ResourceStreamLocator;

import com.vance.logTracker.Exception.MyAccessDeniedPage;
import com.vance.logTracker.Exception.MyInternalErrorPage;
import com.vance.logTracker.Exception.MyPageExpiredPage;
import com.vance.logTracker.persistence.factory.MongodbInstanceFactory;
import com.vance.logTracker.util.TestSamples;
import com.vance.logTracker.view.CBSAggregationPage;
import com.vance.logTracker.view.summary.CBSSummaryPage;
import com.vance.logTracker.view.summary.base.HomePage;

public class LogTrackerApplication extends WebApplication {
	@Override
	public void init() {
		getApplicationSettings().setPageExpiredErrorPage(MyPageExpiredPage.class);
		getApplicationSettings().setAccessDeniedPage(MyAccessDeniedPage.class);
		getApplicationSettings().setInternalErrorPage(MyInternalErrorPage.class);
//		getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
//		IResourceSettings resourceSettings = getResourceSettings();
//		resourceSettings.addResourceFolder("resources/");
//
//		resourceSettings.setResourceStreamLocator(new ResourceStreamLocator());
//
//		System.out.println(resourceSettings.getResourceStreamLocator());

		
		
		
//		final List<Thread> threadList = new ArrayList<Thread>();
//
//		Long startTime = 0L;
//		Long endTime = 0L;
//		List<String> apiLists = new ArrayList<String>();
//		Collection<? extends String> apiTestSamples = TestSamples.get();
//		apiLists.addAll(apiTestSamples);
/*
		String[] dbCollectionNames = { "CBS_ACCESS_LOGS_20111026","CBS_ACCESS_LOGS_20111027",
				"CBS_ACCESS_LOGS_20111028", "CBS_ACCESS_LOGS_20111031",
				"CBS_ACCESS_LOGS_20111101", "CBS_ACCESS_LOGS_20111102",
				"CBS_ACCESS_LOGS_20111103", "CBS_ACCESS_LOGS_20111104" };*/
		
//		String[] dbCollectionNames = {"CBS_ACCESS_LOGS_20111107","CBS_ACCESS_LOGS_20111108","CBS_ACCESS_LOGS_20111109"
//				,"CBS_ACCESS_LOGS_20111110","CBS_ACCESS_LOGS_20111111"};
//		
//		String[] dbCollectionNames = {"CBS_ACCESS_LOGS_20111114","CBS_ACCESS_LOGS_20111115","CBS_ACCESS_LOGS_20111116"};
//		
//		for (final String dbCollectName : dbCollectionNames) {
//
//			ArrayList rfpServiceAPIs = TestSamples.getRfpServiceAPIs();
//			final HashMap<String, ArrayList<String>> servicesAPIMap = new HashMap<String, ArrayList<String>>();
//			servicesAPIMap.put("rfpService", TestSamples.getRfpServiceAPIs());
//			servicesAPIMap.put("userAccountService",
//					TestSamples.getUserAccountServiceAPIs());
//			servicesAPIMap.put("vendorService",
//					TestSamples.getVendorServiceAPIs());
//			servicesAPIMap.put("contactService",
//					TestSamples.getContactServiceAPIs());
//			// aggregatedByService(servicesAPIMap);
//			Set keySet = servicesAPIMap.keySet();
//			final Iterator it = keySet.iterator();
//			while (it.hasNext()) {
//
//				Thread thread = new Thread() {
//
//					String serviceName = it.next().toString();
//
//					public void run() {
//						ServiceAggregation serviceAgg = new ServiceAggregation();
//						serviceAgg.process(servicesAPIMap.get(serviceName),
//								serviceName, dbCollectName);
//					}
//				};
//				threadList.add(thread);
//			}
//		}
//		
//		for (Thread thread : threadList) {
//			try {
//				thread.join();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			thread.start();
//		}
//		
//		String[] dbCollectionNamesForEP = {"EP_ACCESS_LOGS_201111121","EP_ACCESS_LOGS_201111122","EP_ACCESS_LOGS_201111123"};
//		for(String dbCollectionNameForAPP:dbCollectionNamesForEP){
//		ApplicationAggregation.build(new EPDataFileMapping(dbCollectionNameForAPP));
//		
//		}
//		
//		String[] dbCollectionNamesForAM = {"AM_ACCESS_LOGS_20111121","AM_ACCESS_LOGS_20111122","AM_ACCESS_LOGS_20111123"};
//		for(String dbCollectionNameForAPP:dbCollectionNamesForAM){
//			ApplicationAggregation.build(new AMDataFileMapping(dbCollectionNameForAPP));
//		}
//		
//		
//		System.out.println("Done");
//		
//		String[] dbCollectionNamesForSM = {"SM_ACCESS_LOGS_20111121","SM_ACCESS_LOGS_20111122","SM_ACCESS_LOGS_20111123"};
//		for(String dbCollectionNameForAPP:dbCollectionNamesForSM){
//		ApplicationAggregation.build(new SMDataFileMapping(dbCollectionNameForAPP));
//		
//		}
//		
		
//		String[] dbCollectionNamesForNexus = {"Nexus_ACCESS_LOGS_201111121","Nexus_ACCESS_LOGS_20111122","SM_ACCESS_LOGS_20111123"};
//		for(String dbCollectionNameForAPP:dbCollectionNamesForNexus){
//		ApplicationAggregation.build(new NexusDataFileMapping(dbCollectionNameForAPP));
//		
//		}
		
		// Thread backgroundThread=new Thread(){
		// public void run(){
		//
		// // startTime=System.nanoTime();
		// // APIAggregation apiAgg=new APIAggregation();
		// // apiAgg.process(apiLists);
		// // endTime=System.nanoTime();
		// //
		// System.out.println("Finished processing cbs api aggregation....Total Time is: "+(endTime-startTime)/1000000+"ms ");
		// //
		//
		// // ArrayList rfpServiceAPIs=TestSamples.getRfpServiceAPIs();
		// //
		// // HashMap<String,ArrayList<String>> servicesAPIMap=new
		// HashMap<String,ArrayList<String>>();
		// // servicesAPIMap.put("rfpService", TestSamples.getRfpServiceAPIs());
		// // servicesAPIMap.put("userAccountService",
		// TestSamples.getUserAccountServiceAPIs());
		// // servicesAPIMap.put("vendorService",
		// TestSamples.getVendorServiceAPIs());
		// // servicesAPIMap.put("contactService",
		// TestSamples.getContactServiceAPIs());
		// // aggregatedByService(servicesAPIMap);
		// // startTime=System.nanoTime();
		// //// Long startTime=System.nanoTime();
//		  ApplicationAggregation.build(new NexusDataFileMapping("Nexus_ACCESS_LOGS_20111120"));
		// // endTime=System.nanoTime();
		// //
		// System.out.println("Finished processing am applicaiton aggregation....Total Time is: "+(endTime-startTime)/1000000+"ms ");
		// }
		//
		//
		// private void aggregatedByService(final HashMap<String,
		// ArrayList<String>> servicesAPIMap){
		//
		// Set keySet=servicesAPIMap.keySet();
		// final Iterator it=keySet.iterator();
		//
		// Long startTime;
		// Long endTime;
		// startTime=System.nanoTime();
		// while(it.hasNext()){
		// Thread thread=new Thread(){
		// String serviceName=it.next().toString();
		// public void run(){
		// ServiceAggregation serviceAgg=new ServiceAggregation();
		// serviceAgg.process(servicesAPIMap.get(serviceName),serviceName);
		// }
		// };
		// threadList.add(thread);
		// }
		//
		// endTime=System.nanoTime();
		//
		// //
		// System.out.println("Finished processing cbs "+serviceName+" service aggregation....Total Time is: "+(endTime-startTime)/1000000+"ms ");
		// }
		// };
		//
		// backgroundThread.start();

		

	}

	@Override
	public Class<? extends Page> getHomePage() {
		String url = "10.201.10.210";
		MongodbInstanceFactory.connect(url);
		return CBSAggregationPage.class;
//		return HomePage.class;
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new WebSession(request);
	}

}
