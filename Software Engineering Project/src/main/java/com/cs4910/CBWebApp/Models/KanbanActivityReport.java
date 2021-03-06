package com.cs4910.CBWebApp.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.danube.scrumworks.api2.client.*;

public class KanbanActivityReport {

	Product selectedProduct;
	List<Theme>  selectedTheme = new ArrayList<Theme>();
	Boolean includeHistory;
	Date startDate, endDate;
	API2SoapClient client = new API2SoapClient();
	ScrumWorksAPIService apiService = client.getAPIservice();
	String report = "";
	
	public KanbanActivityReport(String selectedProduct, String[]  selectedTheme, Boolean includeHistory, String startDate, String endDate)
	{
		try {
			this.selectedProduct = apiService.getProductByName(selectedProduct);
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Theme> tempTheme = new ArrayList<Theme>();
		try {
			tempTheme =apiService.getThemesForProduct(this.selectedProduct.getId());
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String s: selectedTheme)
		{
			for(Theme t: tempTheme)
			{
				if(s.equals(t.getName()))
				{
					this.selectedTheme.add(t);
				}
					
			}
		}
		this.includeHistory = includeHistory;
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		try {
			date = sdf.parse(startDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			this.startDate = date;
			
		try {
			date = sdf.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			this.endDate = date;	
			
	}
	
	@Override
	public String toString(){
		
		//Get Initial dash based on inputed dates. 
		List<DashboardReleaseStatistics> dash = new ArrayList<DashboardReleaseStatistics>();
		try {
			dash = apiService.getDashboardStatistics(selectedProduct.getId(), startDate, endDate);
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Start object translation Dash -> BacklogItem 
		List<BacklogItem> backlogList = new ArrayList<BacklogItem>();
		for(DashboardReleaseStatistics d : dash)
		{
			try {
				backlogList.addAll(apiService.getBacklogItemsForReleaseInProduct(d.getRelease().getId(), selectedProduct.getId(), false));
			} catch (ScrumWorksException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//BacklogItem -> Sort by selected themes
		List<BacklogItem> tempBacklogList = new ArrayList<BacklogItem>();
		for(BacklogItem b: backlogList)
		{
			for(Theme t: b.getThemes() )
			{
				for(Theme s : selectedTheme)
				{
					if(t.getName().equals(s.getName()))
					{
						if(tempBacklogList.contains(b)){
						//do nothing
						}
						else{ //add b to tempBacklogList
							tempBacklogList.add(b);
						}
					}
				}
			}
		}
		backlogList = tempBacklogList;
		
		
		//Get customs statuses.
		List<BacklogItemStatus> statusType = new ArrayList<BacklogItemStatus>();
		try {
			statusType = apiService.getCustomBacklogItemStatuses(selectedProduct.getId());
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*Started backlog History - Incomplete
		 * //Pulls data needed for backlog History if requested.
		if(includeHistory)
		{
			List<RevisionInfo> revisionInfo = new ArrayList<RevisionInfo>();
			List<BacklogItemChanges> backlogChanges = new ArrayList<BacklogItemChanges>();
			List<BacklogItem> backlogWithChange = new ArrayList<BacklogItem>();
			
			try {
				backlogChanges = apiService.getChangesSinceRevision(selectedProduct.getId(), 0).getBacklogItemChanges();
				
				
			} catch (ScrumWorksException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(BacklogItemChanges bc : backlogChanges)
				{
					for(BacklogItem changedB : bc.getAddedOrChangedEntities())
					{
						for(BacklogItem b : backlogList)
						{
							if(changedB.equals(b))
							{
								backlogWithChange.add(b);
								revisionInfo.add(bc.getRevisionInfo());
							}
						}
					}
					
					
				}
		}*/
		
		
		//Title
		report += "Product: " + selectedProduct.getName() + "<br />";		  
		report += "Themes: ";

		for(Theme t : selectedTheme){
			report += t.getName() + "  ";
		}
		
		report += "Date Range: " + startDate.toString().replaceAll("00:00:00 CST ", "") + " to " + endDate.toString().replaceAll("00:00:00 CST ", "") + "<br /><br />";
		
		for(BacklogItemStatus status : statusType) 
		{
			for(BacklogItem backlog : backlogList) 
			{
				//Backlog matches a status & matches one of the selected themes.
				if(backlog.getStatusId().equals(status.getId())) 
					{
								
						report += "Workflow: " + status.getName() + "<br />" +
									"Title: " + backlog.getName() +" for ";
									for(Theme th : backlog.getThemes())
									{
										report += th.getName() + " ";
									}
										 
						report +="<br />" + "Description: " + backlog.getDescription() + "<br />";
								
						for(DashboardReleaseStatistics d : dash)
						{
							if(d.getRelease().getId().equals(backlog.getReleaseId()))
							{
								report += "Realease: " + d.getRelease().getName() + "<br />";
							}
						}
							
						report += "Effort: " + backlog.getEstimate() + "<br />" + "<br />";
					
						/*//Unfinished - Change History for each backlogItem.
						if(includeHistory)
						{
										
						}*/
					
					}
			}
			
		}
		
		
		return report;
	}
	
	
	
	
	
	
	
public String[] toStringPDF(){
		String[] report;
		List<String> reportPDF = new ArrayList<String>();
		int reportIndex = 0;
		String temp = "";
	
		//Get Initial dash based on inputed dates. 
		List<DashboardReleaseStatistics> dash = new ArrayList<DashboardReleaseStatistics>();
		try {
			dash = apiService.getDashboardStatistics(selectedProduct.getId(), startDate, endDate);
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Start object translation Dash -> BacklogItem 
		List<BacklogItem> backlogList = new ArrayList<BacklogItem>();
		for(DashboardReleaseStatistics d : dash)
		{
			try {
				backlogList.addAll(apiService.getBacklogItemsForReleaseInProduct(d.getRelease().getId(), selectedProduct.getId(), false));
			} catch (ScrumWorksException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//BacklogItem -> Sort by selected themes
		List<BacklogItem> tempBacklogList = new ArrayList<BacklogItem>();
		for(BacklogItem b: backlogList)
		{
			for(Theme t: b.getThemes() )
			{
				for(Theme s : selectedTheme)
				{
					if(t.getName().equals(s.getName()))
					{
						if(tempBacklogList.contains(b)){
						//do nothing
						}
						else{ //add b to tempBacklogList
							tempBacklogList.add(b);
						}
					}
				}
			}
		}
		backlogList = tempBacklogList;
		
		
		//Get customs statuses.
		List<BacklogItemStatus> statusType = new ArrayList<BacklogItemStatus>();
		try {
			statusType = apiService.getCustomBacklogItemStatuses(selectedProduct.getId());
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*Started backlog History - Incomplete
		 * //Pulls data needed for backlog History if requested.
		if(includeHistory)
		{
			List<RevisionInfo> revisionInfo = new ArrayList<RevisionInfo>();
			List<BacklogItemChanges> backlogChanges = new ArrayList<BacklogItemChanges>();
			List<BacklogItem> backlogWithChange = new ArrayList<BacklogItem>();
			
			try {
				backlogChanges = apiService.getChangesSinceRevision(selectedProduct.getId(), 0).getBacklogItemChanges();
				
				
			} catch (ScrumWorksException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(BacklogItemChanges bc : backlogChanges)
				{
					for(BacklogItem changedB : bc.getAddedOrChangedEntities())
					{
						for(BacklogItem b : backlogList)
						{
							if(changedB.equals(b))
							{
								backlogWithChange.add(b);
								revisionInfo.add(bc.getRevisionInfo());
							}
						}
					}
					
					
				}
		}*/
		
		
		//Title
		reportPDF.add("Product: " + selectedProduct.getName());  		  
		
		temp = "";
		for(Theme t : selectedTheme){
			temp += t.getName() + "  ";
		}
		reportPDF.add("Themes: " + temp);
		
		reportPDF.add("Date Range: " + startDate.toString().replaceAll("00:00:00 CST ", "") + " to " + endDate.toString().replaceAll("00:00:00 CST ", ""));
		
		for(BacklogItemStatus status : statusType) 
		{
			for(BacklogItem backlog : backlogList) 
			{
				//Backlog matches a status & matches one of the selected themes.
				if(backlog.getStatusId().equals(status.getId())) 
					{
						temp = "";
						for(Theme th : backlog.getThemes())
						{
							temp += th.getName() + " ";
						}
						reportPDF.add("Workflow: " + status.getName());  
						
						reportPDF.add("Title: " + backlog.getName() +" for " + temp+ "Description: " + backlog.getDescription());
									
								
						for(DashboardReleaseStatistics d : dash)
						{
							if(d.getRelease().getId().equals(backlog.getReleaseId()))
							{
								reportPDF.add("Realease: " + d.getRelease().getName() );
							}
						}
							
						reportPDF.add("Effort: " + backlog.getEstimate());
					
						/*//Unfinished - Change History for each backlogItem.
						if(includeHistory)
						{
										
						}*/
					
					}
			}
			
		}
		report = new String[reportPDF.size()];
		for(String s : reportPDF)
		{
			report[reportIndex++]= s;
		}
		
		return report;
	}
	
	
	
	
}