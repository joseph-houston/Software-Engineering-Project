package com.cs4910.CBWebApp.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.danube.scrumworks.api2.client.*;


public class KanbanWorkflowWarnings 
{

	API2SoapClient client = new API2SoapClient();
	ScrumWorksAPIService apiService = client.getAPIservice();
	Product selectedProduct;	
		
	public KanbanWorkflowWarnings(String selectedProductName)
	{
		try {
			this.selectedProduct = apiService.getProductByName(selectedProductName);
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	private List<BacklogItemStatus> getStatusList(){
		List<BacklogItemStatus> backlogItemStatus = new ArrayList<BacklogItemStatus>();
		try {
			backlogItemStatus = apiService.getCustomBacklogItemStatuses(selectedProduct.getId());
			return backlogItemStatus;
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return backlogItemStatus;
		}
	}
		
	private List<BacklogItem> getBacklogItemsForStatus(List<BacklogItemStatus> items, String status){
		List<BacklogItem> itemList = new ArrayList<BacklogItem>();
			
		try {
			long statusID = 0;
			
			itemList = apiService.getBacklogItemsInProduct(selectedProduct.getId(), true);
			for(BacklogItemStatus s: items){
				if(s.getName()==status)
					statusID = s.getId();
			}
			for(BacklogItem i: itemList){
				if(i.getId()==statusID)
					itemList.add(i);
			}
			return itemList;
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return itemList;
		}
	}
		
	private List<Release> getReleaseList()
	{
		List<Release> releaseList = getReleaseList();
		try {
			releaseList = apiService.getReleasesForProduct(selectedProduct.getId());
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return releaseList;
	}
	
	public String display(){
		String result = "";
		List<BacklogItemStatus> statusList = getStatusList();
		List<BacklogItem> selectedItems = getBacklogItemsForStatus(statusList, "Selected");
		List<BacklogItem> inProgressItems = getBacklogItemsForStatus(statusList, "In Progress");
		List<BacklogItem> impededItems = getBacklogItemsForStatus(statusList, "Impeded");
		List<BacklogItem> uncommittedItems= getBacklogItemsForStatus(statusList, "Uncommitted");
		
		List<Release> releaseList = getReleaseList();
		
		result += selectedReport(selectedItems,releaseList) +
				  inProgressReport(inProgressItems,releaseList) +
				  impededReport(impededItems,releaseList) +
				  umcommittedReport(uncommittedItems,releaseList);
			
		return result;
	}
		
		
	public String selectedReport(List<BacklogItem> selectedItems, List<Release> releaseList)
	{
		String report = "";
		Date today = new Date();
		List<Theme> themeList = new ArrayList<Theme>();
		
		report += "\nWorkflow: Selected\n";
		
		for(BacklogItem b : selectedItems)
		{
			for(Release r : releaseList)
			{
				if(b.getReleaseId() == r.getId() && r.getEndDate().before(today))
				{
					report += "\\\t" + b.getName() + " is past release date. - Release Date: " + r.getEndDate().toString() +
							  "Release: " + r.getName() + " - Themes: ";
					themeList = b.getThemes();
					for(Theme t : themeList)
					{
						report += t.getName() + " ";
					}
					report += "\n";
				}
				
				
				if(b.getReleaseId() == r.getId() && (r.getStartDate().getTime() - today.getTime() / (24 * 60 * 60 * 1000)) > 5)
				{
					report += "\\\t" + b.getName() + ", in workflow longer than specified (5) days. - Release Date: " + r.getEndDate().toString() +
							  "Release: " + r.getName() + " - Themes: ";
					themeList = b.getThemes();
					for(Theme t : themeList)
					{
						report += t.getName() + " ";
					}
					report += "\n";
				}
				
			}
		}
		
		if(selectedItems.size() > 15)
			report += "\\tTask Capacity Exceeded in Selected Workflow (max capacity currently set at 15)\n";
		
		return report;
	}
	
	public String inProgressReport(List<BacklogItem> inProgressItems, List<Release> releaseList)
	{
		String report = "";
		Date today = new Date();
		List<Theme> themeList = new ArrayList<Theme>();
		
		report += "\nWorkflow: In Progress\n";
		
		for(BacklogItem b : inProgressItems)
		{
			for(Release r : releaseList)
			{
				if(b.getReleaseId() == r.getId() && r.getEndDate().before(today))
				{
					report += "\\\t" + b.getName() + " is past release date. - Release Date: " + r.getEndDate().toString() +
							  "Release: " + r.getName() + " - Themes: ";
					themeList = b.getThemes();
					for(Theme t : themeList)
					{
						report += t.getName() + " ";
					}
					report += "\n";
				}
				
				
				if(b.getReleaseId() == r.getId() && (r.getStartDate().getTime() - today.getTime() / (24 * 60 * 60 * 1000)) > 10)
				{
					report += "\\\t" + b.getName() + ", in workflow longer than specified (10) days. - Release Date: " + r.getEndDate().toString() +
							  "Release: " + r.getName() + " - Themes: ";
					themeList = b.getThemes();
					for(Theme t : themeList)
					{
						report += t.getName() + " ";
					}
					report += "\n";
				}
				
			}
		}
		
		if(inProgressItems.size() > 15)
			report += "\\tTask Capacity Exceeded in In Progress Workflow (max capacity currently set at 15)\n";
		
		return report;
	}
	
	public String impededReport(List<BacklogItem> impededItems, List<Release> releaseList)
	{
		String report = "";
		Date today = new Date();
		List<Theme> themeList = new ArrayList<Theme>();
		
		report += "\nWorkflow: Impeded\n";
		
		for(BacklogItem b : impededItems)
		{
			for(Release r : releaseList)
			{
				if(b.getReleaseId() == r.getId() && r.getEndDate().before(today))
				{
					report += "\\\t" + b.getName() + " is past release date. - Release Date: " + r.getEndDate().toString() +
							  "Release: " + r.getName() + " - Themes: ";
					themeList = b.getThemes();
					for(Theme t : themeList)
					{
						report += t.getName() + " ";
					}
					report += "\n";
				}
				
				
				if(b.getReleaseId() == r.getId() && (r.getStartDate().getTime() - today.getTime() / (24 * 60 * 60 * 1000)) > 5)
				{
					report += "\\\t" + b.getName() + ", in workflow longer than specified (5) days. - Release Date: " + r.getEndDate().toString() +
							  "Release: " + r.getName() + " - Themes: ";
					themeList = b.getThemes();
					for(Theme t : themeList)
					{
						report += t.getName() + " ";
					}
					report += "\n";
				}
				
			}
		}
		
		if(impededItems.size() > 5)
			report += "\\tTask Capacity Exceeded in Impeded Workflow (max capacity currently set at 5)\n";
		
		
		return report;
	}
	
	public String umcommittedReport(List<BacklogItem> uncommittedItems, List<Release> releaseList)
	{
		String report = "";
		Date today = new Date();
		
		report += "\nWorkflow: Uncommitted\n";
		for(BacklogItem b : uncommittedItems)
		{
			for(Release r : releaseList)
			{
				if(b.getReleaseId() == r.getId() && r.getEndDate().before(today) && r.isArchived() == false)
				{
					report += "\\tVerify release date for " + r.getName() + " since it has uncompleted tasks - Release date: " + r.getEndDate().toString() + "\n";
				}
				
				if(b.getReleaseId() == r.getId() && 
				  (r.getEndDate().getTime()/ (24 * 60 * 60 * 1000))  >  ((today.getTime()/ (24 * 60 * 60 * 1000)) + b.getEstimate()) )
				{
					report += "\\tWarning - Release " + r.getId().toString() + " - " + r.getName() + " has uncommitted work that may not be completed - Release Date: " + r.getEndDate().toString() +"\n";
				}
			}
		}
		
		return report;
	}
	
	
		/*Version 0.75
		 * @Override
		public String toString()
		{

			String report = "";
			List<Task> taskList = new ArrayList<Task>();
			List<Task> selectedTaskList = new ArrayList<Task>();
			List<BacklogItem> selectedBacklogItem = new ArrayList<BacklogItem>();
			List<Task> inProgressTaskList = new ArrayList<Task>();
			List<BacklogItem> inProgressBacklogItem = new ArrayList<BacklogItem>();
			List<Task> impededTaskList = new ArrayList<Task>();
			List<BacklogItem> impededBacklogItem = new ArrayList<BacklogItem>();
			List<Task> uncommittedTaskList = new ArrayList<Task>();
			List<BacklogItem> uncommittedBacklogItem = new ArrayList<BacklogItem>();
			List<Release> releaseList = new ArrayList<Release>();
			Date today = new Date();
			List<Theme> themeList = new ArrayList<Theme>();
			
			try {
				releaseList = apiService.getReleasesForProduct(selectedProduct.getId());
			} catch (ScrumWorksException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				taskList = apiService.getTasksForProduct(selectedProduct.getId());
			} catch (ScrumWorksException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			for(Task t : taskList)
			{
				if(t.getStatus().toLowerCase() == "selected")
					selectedTaskList.add(t);
					
				if(t.getStatus().toLowerCase() == "in progress")
					inProgressTaskList.add(t);
				
				if(t.getStatus().toLowerCase() == "impededId")
					impededTaskList.add(t);
				
				if(t.getStatus().toLowerCase() == "uncommitted")
					uncommittedTaskList.add(t);
			}
							
			
			
			for(Task t : selectedTaskList)
			{
				try {
					selectedBacklogItem.add(apiService.getBacklogItemById(t.getBacklogItemId()));
				} catch (ScrumWorksException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for(Task t : inProgressTaskList)
			{
				try {
					inProgressBacklogItem.add(apiService.getBacklogItemById(t.getBacklogItemId()));
				} catch (ScrumWorksException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for(Task t : impededTaskList)
			{
				try {
					impededBacklogItem.add(apiService.getBacklogItemById(t.getBacklogItemId()));
				} catch (ScrumWorksException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for(Task t : uncommittedTaskList)
			{
				try {
					uncommittedBacklogItem.add(apiService.getBacklogItemById(t.getBacklogItemId()));
				} catch (ScrumWorksException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			report += "Workflow: Selected\n";
			
			for(BacklogItem b : selectedBacklogItem)
			{
				for(Release r : releaseList)
				{
					if(b.getReleaseId() == r.getId() && r.getEndDate().before(today))
					{
						report += "\\\t" + b.getName() + " is past release date. - Release Date: " + r.getEndDate().toString() +
								  "Release: " + r.getName() + " - Themes: ";
						themeList = b.getThemes();
						for(Theme t : themeList)
						{
							report += t.getName() + " ";
						}
						report += "\n";
					}
					
					
					if(b.getReleaseId() == r.getId() && (r.getStartDate().getTime() - today.getTime() / (24 * 60 * 60 * 1000)) > 5)
					{
						report += "\\\t" + b.getName() + ", in workflow longer than specified (5) days. - Release Date: " + r.getEndDate().toString() +
								  "Release: " + r.getName() + " - Themes: ";
						themeList = b.getThemes();
						for(Theme t : themeList)
						{
							report += t.getName() + " ";
						}
						report += "\n";
					}
					
				}
			}
			
			if(selectedBacklogItem.size() > 15)
				report += "\\tTask Capacity Exceeded in Selected Workflow (max capacity currently set at 15)\n";
			
			
			
			report += "\nWorkflow: In Progress\n";
			
			for(BacklogItem b : inProgressBacklogItem)
			{
				for(Release r : releaseList)
				{
					if(b.getReleaseId() == r.getId() && r.getEndDate().before(today))
					{
						report += "\\\t" + b.getName() + " is past release date. - Release Date: " + r.getEndDate().toString() +
								  "Release: " + r.getName() + " - Themes: ";
						themeList = b.getThemes();
						for(Theme t : themeList)
						{
							report += t.getName() + " ";
						}
						report += "\n";
					}
					
					
					if(b.getReleaseId() == r.getId() && (r.getStartDate().getTime() - today.getTime() / (24 * 60 * 60 * 1000)) > 10)
					{
						report += "\\\t" + b.getName() + ", in workflow longer than specified (10) days. - Release Date: " + r.getEndDate().toString() +
								  "Release: " + r.getName() + " - Themes: ";
						themeList = b.getThemes();
						for(Theme t : themeList)
						{
							report += t.getName() + " ";
						}
						report += "\n";
					}
					
				}
			}
			
			if(inProgressBacklogItem.size() > 15)
				report += "\\tTask Capacity Exceeded in In Progress Workflow (max capacity currently set at 15)\n";

			
			
			report += "\nWorkflow: Impeded\n";
			
			for(BacklogItem b : impededBacklogItem)
			{
				for(Release r : releaseList)
				{
					if(b.getReleaseId() == r.getId() && r.getEndDate().before(today))
					{
						report += "\\\t" + b.getName() + " is past release date. - Release Date: " + r.getEndDate().toString() +
								  "Release: " + r.getName() + " - Themes: ";
						themeList = b.getThemes();
						for(Theme t : themeList)
						{
							report += t.getName() + " ";
						}
						report += "\n";
					}
					
					
					if(b.getReleaseId() == r.getId() && (r.getStartDate().getTime() - today.getTime() / (24 * 60 * 60 * 1000)) > 5)
					{
						report += "\\\t" + b.getName() + ", in workflow longer than specified (5) days. - Release Date: " + r.getEndDate().toString() +
								  "Release: " + r.getName() + " - Themes: ";
						themeList = b.getThemes();
						for(Theme t : themeList)
						{
							report += t.getName() + " ";
						}
						report += "\n";
					}
					
				}
			}
			
			if(impededBacklogItem.size() > 15)
				report += "\\tTask Capacity Exceeded in Impeded Workflow (max capacity currently set at 15)\n";

			
			report += "\nWorkflow: Uncommitted\n";
			for(BacklogItem b : uncommittedBacklogItem)
			{
				for(Release r : releaseList)
				{
					if(b.getReleaseId() == r.getId() && r.getEndDate().before(today) && r.isArchived() == false)
					{
						report += "\\tVerify release date for " + r.getName() + " since it has uncompleted tasks - Release date: " + r.getEndDate().toString() + "\n";
					}
					
					if(b.getReleaseId() == r.getId() && 
					  (r.getEndDate().getTime()/ (24 * 60 * 60 * 1000))  >  ((today.getTime()/ (24 * 60 * 60 * 1000)) + b.getEstimate()) )
					{
						report += "\\tWarning - Release " + r.getId().toString() + " - " + r.getName() + " has uncommitted work that may not be completed - Release Date: " + r.getEndDate().toString() +"\n";
					}
				}
			}
			
			return report;
		
		}*/
}



