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
		
		List<Release> releaseList = new ArrayList<Release>();
		try {
			releaseList = apiService.getReleasesForProduct(selectedProduct.getId());
		} catch (ScrumWorksException e) {
			e.printStackTrace();
		}
		
		report += "Product: " + selectedProduct.getName() + "\n";
				  
		
		report += "\n" + "Date Range: " + startDate.toString() + " to " + endDate.toString() + "\n\n";
		
		for(int t = 0; t < selectedTheme.length; t++)
		{
			List<BacklogItem> backlogList = new ArrayList<BacklogItem>();
			try {
				backlogList = apiService.getBacklogItemsForThemeInProduct(selectedTheme[t].getId(), selectedProduct.getId(), includeHistory);
			} catch (ScrumWorksException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List<BacklogItemStatus> statusType = new ArrayList<BacklogItemStatus>();
			try {
				statusType = apiService.getCustomBacklogItemStatuses(selectedProduct.getId());
			} catch (ScrumWorksException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(statusType.isEmpty() == false && backlogList.isEmpty() == false) 
			{
				for(int s = 0; s < statusType.size(); s++)
				{
					for(int b = 0; b < backlogList.size(); b++) 
					{
						if(backlogList.get(b).getStatusId() == statusType.get(s).getId())
						{
							report += "Workflow: " + statusType.get(s).toString() + "\n" +
									  "Title: " + backlogList.get(b).getName() + "\n" +
									  "Description: " + backlogList.get(b).getDescription() + "\n" +
									  "Realease: ";
							
							for(int r = 0; r < releaseList.size(); r++)
							{
								if(releaseList.get(r).getId() == backlogList.get(b).getReleaseId())
								{
									report += releaseList.get(r).getDescription() + "\n";
								}
							}
							
							report += "Effort: " + backlogList.get(b).getEstimate() + "\n";
							
							if(includeHistory == true)
							{								
								FilterChangesByType type = new FilterChangesByType();
								type.setIncludeBacklogItems(true);
								try {
									report +=apiService.getChangesSinceRevisionForTypes(selectedProduct.getId(), 0,type).toString();
								} catch (ScrumWorksException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								report += "\n";
							}
						}
					}
				}
			}
		}
		return report;
	}
}
