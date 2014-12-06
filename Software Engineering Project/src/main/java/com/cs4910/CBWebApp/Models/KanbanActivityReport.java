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
	List<Theme>  selectedTheme;
	Boolean includeHistory;
	Date startDate, endDate;
	API2SoapClient client = new API2SoapClient();
	ScrumWorksAPIService apiService = client.getAPIservice();
	String report = "";
	
	public KanbanActivityReport(String selectedProduct, String[]  selectedTheme, String includeHistory, String startDate, String endDate)
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
				if(s == t.getName())
				{
					this.selectedTheme.add(t);
				}
					
			}
		}
		
		if(includeHistory.toLowerCase() == "true")
			this.includeHistory = true;
		else
			this.includeHistory = false;
		
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
					if(t == s)
					{
						tempBacklogList.add(b);
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
							if(changedB == b)
							{
								backlogWithChange.add(b);
								revisionInfo.add(bc.getRevisionInfo());
							}
						}
					}
					
					
				}
		}*/
		
		
		//Title
		report += "Product: " + selectedProduct.getName() + "\n";		  
		report += "Themes: ";
		for(Theme t : selectedTheme)
			report += t.getName() + "  ";
		
		report += "Date Range: " + startDate.toString() + " to " + endDate.toString() + "\n\n";
		
		for(BacklogItemStatus status : statusType) 
		{
			for(BacklogItem backlog : backlogList) 
			{
				for(Theme t: backlog.getThemes() )
				{
					for(Theme s : selectedTheme)
					{
						//Backlog matches a status & matches one of the selected themes.
						if(backlog.getStatusId() == status.getId() && t == s) 
							{
								
								report += "Workflow: " + status.toString() + "\n" +
											"Title: " + backlog.getName() +" for " + t.getName()  + "\n" +
											"Description: " + backlog.getDescription() + "\n";
								
									for(DashboardReleaseStatistics d : dash)
										{
											if(d.getRelease().getId() == backlog.getReleaseId())
											{
												report += "Realease: " + d.getRelease().getDescription() + "\n";
											}
										}
							
									report += "Effort: " + backlog.getEstimate() + "\n";
					
									/*//Unfinished - Change History for each backlogItem.
									if(includeHistory)
									{
										
									}*/
					
							}
					}
				}
			}
		}
		
		
		return report;
	}
}