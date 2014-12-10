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
				backlogItemChanges = apiService.getChangesSinceRevision(selectedProduct.getId(), 1).getBacklogItemChanges();
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
		//If the selectedUser has not made any updates in the given date range than it will break the app.
		for(RevisionInfo rInfo : revisionInfo)
		{
			if(rInfo.getUserName().equals(selectedUser.getName()))
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
		
		
//		//Change String from API to Date.
//		String month, changedDate;
//		Date newDate = new Date();
//		for(RevisionInfo rInfo : usersRevisionInfo)
//		{
//			changedDate = "";//Reset between rInfos
//			month = rInfo.getTimeStamp().toString().substring(4, 7);
//			switch(Day.valueOf(month))
//			{
//				case Dec:
//					changedDate += "12/";
//					break;
//				case Nov:
//					changedDate += "11/";
//					break;
//				case Oct:
//					changedDate += "10/";
//					break;
//				case Feb:
//					changedDate += "02/";
//					break;
//				case Mar:
//					changedDate += "03/";
//					break;
//				case Apr:
//					changedDate += "04/";
//					break;
//				case May:
//					changedDate += "05/";
//					break;
//				case Jun:
//					changedDate += "06/";
//					break;
//				case Jul:
//					changedDate += "07/";
//					break;
//				case Aug:
//					changedDate += "08/";
//					break;
//				default:
//					changedDate += "01/";;
//						
//			}
//			
//			changedDate += rInfo.getTimeStamp().toString().substring(8,10) + "/";
//			changedDate += rInfo.getTimeStamp().toString().substring(24, 28);
//			
//			try {
//				newDate = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(changedDate);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			rInfo.setTimeStamp(newDate);
//		}
//		
//		
//		
//		//Creates a tempList that will add all RevisionInfo that are between specified dates.
//		ArrayList<RevisionInfo> tempList = new ArrayList<RevisionInfo>();
//		for(RevisionInfo rInfo: usersRevisionInfo)
//		{			
//			//If rInfo.getTimeStamp() is between start and end Date then tempList.add(rInfo).
//			if( rInfo.getTimeStamp().after(startDate) && rInfo.getTimeStamp().before(endDate) )
//			{
//				tempList.add(rInfo);
//			}
//		}
//		usersRevisionInfo = tempList;
//		ArrayList<RevisionInfo> tempList = new ArrayList<RevisionInfo>();
//		//Each index of the List is a List of every change made on that day. 
//		ArrayList<ArrayList<RevisionInfo>> eachDay = new ArrayList<ArrayList<RevisionInfo>>();
//		for(RevisionInfo rInfo: usersRevisionInfo)
//		{
//			if( eachDay.isEmpty())
//			{
//				tempList.clear();
//				tempList.add(rInfo);
//				eachDay.add(tempList);
//			}
//			else
//			{
//				innerloop:for(ArrayList<RevisionInfo> ed : eachDay )
//				{
//					if(ed.get(0).getTimeStamp().equals(rInfo.getTimeStamp()))
//					{
//						ed.add(rInfo);
//					}
//					else
//					{
//							tempList.clear();
//							tempList.add(rInfo);
//							eachDay.add(tempList);
//							break innerloop;
//					}
//				}		
//			}
//		}
//		
//		for(ArrayList<RevisionInfo> ed : eachDay )
//		{
//		System.out.println(ed.size());
//		}
		
		//For every day that has revisions sort the revisions by what they updated (Sprint/Kanban) and by who for.
		//Find a way to sort each revision on a day by what they updated and who they updated for.
		//Then update the commented report += near 226.
		
//		for(ArrayList<RevisionInfo> ed : eachDay)
//		{
//			//report+= ed.get(0).getTimeStamp().toString().replaceAll("00:00:00 CST ", "") + " - " + ed.size() + " updates in X" + "<br />";		
//			
//			
//			
//			
//			
//			for(RevisionInfo specificDay : ed)
//			{
//				report += specificDay.getTimeStamp().toString().replaceAll("00:00:00 CST ", "") + " - " + " updates in Xyz" + "<br />";
//				
//				//includeDetails here on each specific day.
//			}
//		}
		try{
		int date;
		int month;
		int year;
		int oldDate = usersRevisionInfo.get(0).getTimeStamp().getDate();
		int oldMonth = usersRevisionInfo.get(0).getTimeStamp().getMonth() + 1;
		int oldYear = usersRevisionInfo.get(0).getTimeStamp().getYear() + 1900;
		String report_append = "";
		int updateCount=1;
		
		for(RevisionInfo uRI : usersRevisionInfo)
		{
			date = uRI.getTimeStamp().getDate();
			month = uRI.getTimeStamp().getMonth() + 1;
			year = uRI.getTimeStamp().getYear() + 1900;

			System.out.println(date + " " + oldDate);
			if(date == oldDate && month == oldMonth && year == oldYear && usersRevisionInfo.get(0).getRevisionNumber() != uRI.getRevisionNumber()){
				updateCount++;
				report_append = month + "/" + date + "/" + year + " - " + updateCount + " updates in " +apiService.getTeamById(selectedProduct.getTeamIds().get(0)).getType()  + "<br />";

			}
			else{
				if(usersRevisionInfo.get(1).getRevisionNumber() != uRI.getRevisionNumber()){
						report += report_append;
						report += month + "/" + date + "/" + year + " - " + updateCount + " updates in "+ apiService.getTeamById(selectedProduct.getTeamIds().get(0)).getType()+ "<br />";
				}
				oldDate = date;
				oldMonth = month;
				oldYear = year;
				if(usersRevisionInfo.get(1).getRevisionNumber() != uRI.getRevisionNumber())
					updateCount = 0;
				else
					updateCount = 1;
				report_append = "";
			}
		}
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		report += "Number of Days: " + usersRevisionInfo.size() + "<br />";
		
//		int numberOfUpdates = 0;
//		for(ArrayList<RevisionInfo> ed : eachDay)
//		{
//			numberOfUpdates = ed.size();
//		}
		report += "Number of Updates: " + usersRevisionInfo.size() + "<br />";
		
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
