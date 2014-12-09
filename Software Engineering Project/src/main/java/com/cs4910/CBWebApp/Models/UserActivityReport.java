package com.cs4910.CBWebApp.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
	
	 enum Day
	 {
	     Dec, Nov, Oct, Feb, Mar, Apr, May, Jun, Jul, Aug //Default is Jan no listing it.
	 }
	
	
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
		
		List<RevisionInfo> usersRevisionInfo = new ArrayList<RevisionInfo>();
		for(RevisionInfo rInfo : revisionInfo)
		{
			if(rInfo.getUserName().equals("administrator"))
			{
				usersRevisionInfo.add(rInfo);
			}
		}
		
//		//Tue Dec 09 11:40:22 CST 2014
//		Date changedDate = new Date();
//		try {
//			changedDate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(usersRevisionInfo.get(0).getTimeStamp().toString().equalsIgnoreCase(usersRevisionInfo.get(0).getTimeStamp().toString().substring(10,22)));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		report += "User Activity for " + selectedUser.getUserName() + " from " + 
				  startDate.toString().replaceAll("00:00:00 CST ", "") + " to " + endDate.toString().replaceAll("00:00:00 CST ", "") + "<br />";
		
		
		//Change String from API to Date.
		String month, changedDate;
		Date newDate = new Date();
		for(RevisionInfo rInfo : usersRevisionInfo)
		{
			changedDate = "";//Reset between rInfos
			month = rInfo.getTimeStamp().toString().substring(4, 6);
			switch(Day.valueOf(month))
			{
				case Dec:
					changedDate += "12/";
					break;
				case Nov:
					changedDate += "11/";
					break;
				case Oct:
					changedDate += "10/";
					break;
				case Feb:
					changedDate += "02/";
					break;
				case Mar:
					changedDate += "03/";
					break;
				case Apr:
					changedDate += "04/";
					break;
				case May:
					changedDate += "05/";
					break;
				case Jun:
					changedDate += "06/";
					break;
				case Jul:
					changedDate += "07/";
					break;
				case Aug:
					changedDate += "08/";
					break;
				default:
					changedDate += "01/";;
						
			}
			
			changedDate += rInfo.getTimeStamp().toString().substring(8,9) + "/";
			changedDate += rInfo.getTimeStamp().toString().substring(24, 27);
			
			try {
				newDate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(changedDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rInfo.setTimeStamp(newDate);
		}
		
		
		
		//Creates a tempList that will add all RevisionInfo that are between specified dates.
		ArrayList<RevisionInfo> tempList = new ArrayList<RevisionInfo>();
		for(RevisionInfo rInfo: usersRevisionInfo)
		{			
			//If rInfo.getTimeStamp() is between start and end Date then tempList.add(rInfo).
			if( rInfo.getTimeStamp().after(startDate) && rInfo.getTimeStamp().before(endDate) )
			{
				tempList.add(rInfo);
			}
		}
		usersRevisionInfo = tempList;
		
		//Each index of the List is a List of every change made on that day. 
		ArrayList<ArrayList<RevisionInfo>> eachDay = new ArrayList<ArrayList<RevisionInfo>>();
		for(RevisionInfo rInfo: usersRevisionInfo)
		{
			if( eachDay.isEmpty())
			{
				eachDay.get(0).add(rInfo);
			}
			else
			{
				for(ArrayList<RevisionInfo> ed : eachDay )
				{
					if(ed.get(0).getTimeStamp().equals(rInfo.getTimeStamp()))
					{
						ed.add(rInfo);
					}
					else
					{
							tempList.clear();
							tempList.add(rInfo);
							eachDay.add(tempList);
					}
				}		
			}
		}
		
		
		//For every day that has revisions sort the revisions by what they updated (Sprint/Kanban) and by who for.
		//Find a way to sort each revision on a day by what they updated and who they updated for.
		//Then update the commented report += near 226.
		
		for(ArrayList<RevisionInfo> ed : eachDay)
		{
			report+= ed.get(0).getTimeStamp().toString().replaceAll("00:00:00 CST ", "") + " - " + ed.size() + " updates in X" + "<br />";		
			
			
//			
//			report += ed.get(0).getTimeStamp().toString().replaceAll("00:00:00 CST ", "") +
//					  " - " + ed.size() + " updates in" 
//			
//			for(RevisionInfo specificDay : ed)
//			{
//				//includeDetails here on each specific day.
//			}
		}
		
		report += "Number of Days: " + eachDay.size() + "<br />";
		
		int numberOfUpdates = 0;
		for(ArrayList<RevisionInfo> ed : eachDay)
		{
			numberOfUpdates = ed.size();
		}
		report += "Number of Updates: " + numberOfUpdates + "<br />";
		
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
