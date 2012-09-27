package com.vance.logTracker.util;

import java.util.ArrayList;
import java.util.Collection;

public class TestSamples {
	
	public enum ContactService {
		setContactOwner,createUpdateContacts,getContactsByEmailAddresses,
		createUpdateAccountContacts,
		getContacts,createUpdateContactAsOwner,
		getContactsByUserOID;
	}
	
	public enum RfpService {

		createRfp, search, setRfpRecipients, createUpdateResponse,
		getChanges, createUpdateVendorResponse, getRfp, updateRfp, 
		changeVendorStatus, changeStatus, setPermissions, getResponseStatusSummary, 
		getChangeDetails, getResponse, getResponseStatusDetails, getVendorResponse, 
		getVendorStatus, getStatus;}
	
	public enum UserAccountService {
		searchUser,createUserWithInvitationToken,authenticateByEmailAddress,createUpdateUser,
		getResetPasswordToken,createUpdateUserGroup,createUpdateAccount,getUser,getUserGroup,
		createUpdateRole,searchUserGroup,getInvitationToken,resetPassword,getRolesByAccountOID,
		changePassword,getAccount,impersonate,
		getUserGroupsByAccountOID,authenticate,getRole,validateInvitationToken,searchAccount,validateToken;
	}
	
	public enum VendorService {
		createSupplierAccount,deleteMeetingRooms,deleteLocalAttractions,
		createUpdateLeadCenter,createUpdateVendor,getSupplierOIDsByContactOID,
		getMeetingRoomsByVendorOIDs,getChangeDetails,createUpdateMeetingRooms,
		getSupplierContacts,searchVendor,getVendors,getLocalAttractionsByVendorOIDs,
		getSupplierAccountOID,createUpdateLocalAttractions,setVendorMapping,findLeadCenter,
		getLeadCenters,getPromotion,convertExternalVendorIDs,getDistributionRules,
		validateSupplierOIDs,getBrands,getAffiliations,getChains,convertVendorOIDs
		,getLocationSuggestion,searchChain,getSupplierRelation,searchBrand,setSupplierRelation,setFeaturedFactor;
	}

	
	

	public static Collection<? extends String> get() {
		 ArrayList<String> apiList=new ArrayList<String>();

		 
//		 apiList.add("getRfp");

		 apiList.add("createRfp");
////
//		 apiList.add("getResponseStatusSummary");
//////
//		 apiList.add("search");
////
//		 apiList.add("setPermissions");
////		 
//		 apiList.add("getVendorResponse");
////
//		 apiList.add("changeStatus");
////
//		 apiList.add("changeVendorStatus");
////
//		 apiList.add("createUpdateResponse");
////
//		 apiList.add("createUpdateVendorResponse");
////		 
//		 apiList.add("getChangeDetails");
////		 
//		 apiList.add("getResponse");
////		 
//		 apiList.add("getStatus");
//		 
//		 apiList.add("setRfpRecipients");
////		 
//		 apiList.add("updateRfp");
		 
		 return apiList;
	}
	
	
	public static ArrayList<String> getRfpServiceAPIs(){
		ArrayList<String> apiNames=new ArrayList<String>();
		for(RfpService rfpServiceAPIName:RfpService.values()){
			apiNames.add(rfpServiceAPIName.toString());
		}
		return apiNames;
	}
	
	public static ArrayList<String> getVendorServiceAPIs(){
		ArrayList<String> apiNames=new ArrayList<String>();
		for(VendorService rfpServiceAPIName:VendorService.values()){
			apiNames.add(rfpServiceAPIName.toString());
		}
		return apiNames;
	}
	
	public static ArrayList<String> getContactServiceAPIs(){
		ArrayList<String> apiNames=new ArrayList<String>();
		for(ContactService rfpServiceAPIName:ContactService.values()){
			apiNames.add(rfpServiceAPIName.toString());
		}
		return apiNames;
	}
	
	public static ArrayList<String> getUserAccountServiceAPIs(){
		ArrayList<String> apiNames=new ArrayList<String>();
		for(UserAccountService rfpServiceAPIName:UserAccountService.values()){
			apiNames.add(rfpServiceAPIName.toString());
		}
		return apiNames;
	}
	
	
	
}
