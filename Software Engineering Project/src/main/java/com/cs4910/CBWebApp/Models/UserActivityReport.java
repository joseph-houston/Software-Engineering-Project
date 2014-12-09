package com.cs4910.CBWebApp.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.danube.scrumworks.api2.client.*;

public class UserActivityReport {
	
	API2SoapClient client = new API2SoapClient();
	ScrumWorksAPIService apiService = client.getAPIservice();
	String report = "";
	Product selectedProduct;
	User selectedUser;
	Boolean includeDetails;
	Date startDate, endDate;
	
	public UserActivityReport(String selectedProduct, String selectedUser, Boolean includeDetails, String startDate, String endDate)
	{
		try {
			this.selectedProduct = apiService.getProductByName(selectedProduct);
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.selectedUser = apiService.getUserByUserName(selectedUser);
		} catch (ScrumWorksException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		this.includeDetails = includeDetails;
		
		Date date = new Date();
		try {
			date = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			this.startDate = date;
			
		try {
			date = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			this.endDate = date;
	}
	
	@Override
	public String toString()
	{
		//Object translation based on inputed dates. Dash -> backChanges -> Revision
		List<DashboardReleaseStatistics> dash = new ArrayList<DashboardReleaseStatistics>();
		try {
			dash = apiService.getDashboardStatistics(selectedProduct.getId(), startDate, endDate);
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<BacklogItemChanges> backlogItemChanges = new ArrayList<BacklogItemChanges>();
		for(DashboardReleaseStatistics d : dash)
		{

			try {
				backlogItemChanges = apiService.getChangesSinceRevision(selectedProduct.getId(), 0).getBacklogItemChanges();
			} catch (ScrumWorksException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		List<RevisionInfo> revisionInfo = new ArrayList<RevisionInfo>();
		for(BacklogItemChanges b : backlogItemChanges)
		{
			
			revisionInfo.add(b.getRevisionInfo());
		}
		
		int i = 0;
		List<RevisionInfo> usersRevisionInfo = new ArrayList<RevisionInfo>();
		for(RevisionInfo rInfo : revisionInfo)
		{
			System.out.println("Step 1");
			System.out.println(rInfo.getUserName() + " " + selectedUser.getUserName());
			if(rInfo.getUserName().equals("administrator"))
			{
				usersRevisionInfo.add(rInfo);
				System.out.println("Activates");
				System.out.println(usersRevisionInfo.get(i).getTimeStamp());
				i++;
			}
		}
		
		report += "User Activity for " + selectedUser.getUserName() + " from " + 
				  startDate.toString().replaceAll("00:00:00 CST ", "") + " to " + endDate.toString().replaceAll("00:00:00 CST ", "") + "<br />";
		
		
		return report;
	}
}




/* Version 0.1
 * List<DashboardReleaseStatistics> dash = new ArrayList<DashboardReleaseStatistics>();
 
		try {
			dash = apiService.getDashboardStatistics(selectedProduct.getId(), startDate, endDate);
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Release> releaseList = new ArrayList<Release>();
		for(DashboardReleaseStatistics d : dash)
		{
			releaseList.add(d.getRelease());
		}
		
		List<BacklogItem> backlogList = new ArrayList<BacklogItem>();
		for(Release r : releaseList)
		{
			try {
				backlogList.addAll(apiService.getBacklogItemsInRelease(r.getId(), false));
			} catch (ScrumWorksException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		List<ProductChanges> productChanges = new ArrayList<ProductChanges>();
		try {
			productChanges.addAll(apiService.getChangesSinceRevision(selectedProduct.getId(), 0).getProductChanges());
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
