package com.cs4910.CBWebApp.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.danube.scrumworks.api2.client.*;

public class KanbanActivityReport {

	Product selectedProduct;
	Theme[] selectedTheme;
	Boolean includeHistory;
	Date startDate, endDate;
	
	public KanbanActivityReport(Product selectedProduct, Theme[] selectedTheme, Boolean includeHistory, Date startDate, Date endDate)
	{
		this.selectedProduct = selectedProduct;
		this.selectedTheme = selectedTheme;
		this.includeHistory = includeHistory;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	@Override
	public String toString(){
		API2SoapClient client = new API2SoapClient();
		ScrumWorksAPIService apiService = client.getAPIservice();
		String report = "";
		
		List<DashboardReleaseStatistics> dash = new ArrayList<DashboardReleaseStatistics>();
		try {
			dash = apiService.getDashboardStatistics(selectedProduct.getId(), startDate, endDate);
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		List<BacklogItemStatus> statusType = new ArrayList<BacklogItemStatus>();
		try {
			statusType = apiService.getCustomBacklogItemStatuses(selectedProduct.getId());
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		report += "Product: " + selectedProduct.getName() + "\n";		  
		report += "Themes: ";
		for(Theme t : selectedTheme)
			report += t.getName() + "  ";
		
		report += "Date Range: " + startDate.toString() + " to " + endDate.toString() + "\n\n";
		
		for(BacklogItemStatus status : statusType)
		{
			for(BacklogItem backlog : backlogList) 
			{
				if(backlog.getStatusId() == status.getId())
				{
					report += "Workflow: " + status.toString() + "\n" +
							   "Title: " + backlog.getName() + "\n" +
							   "Description: " + backlog.getDescription() + "\n";
								
					for(DashboardReleaseStatistics d : dash)
					{
						if(d.getRelease().getId() == backlog.getReleaseId())
						{
							report += "Realease: " + d.getRelease().getDescription() + "\n";
						}
					}
							
					report += "Effort: " + backlog.getEstimate() + "\n";
					
					//Unfinished - Change History for each backlogItem.
					
				}
			}
		}
		
		
		return report;
	}
}