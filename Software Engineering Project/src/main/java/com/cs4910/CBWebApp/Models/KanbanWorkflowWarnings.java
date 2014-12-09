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
		List<BacklogItem> fullItemList = new ArrayList<BacklogItem>();
		List<BacklogItem> sortedItemList = new ArrayList<BacklogItem>();
			
		try {
			long statusID = 0;
			
			fullItemList = apiService.getBacklogItemsInProduct(selectedProduct.getId(), true);
			for(BacklogItemStatus s: items){
				if(s.getName().equals(status)){
					statusID = s.getId();
				}
			}
			for(BacklogItem i: fullItemList){
				if(i.getStatusId().equals(statusID)){
					sortedItemList.add(i);
				}
			}
			
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sortedItemList;
	}
		
	private List<Release> getReleaseList()
	{
		List<Release> releaseList = new ArrayList<Release>();
		try {
			releaseList = apiService.getReleasesForProduct(selectedProduct.getId());
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return releaseList;
	}
	
	public String display(){
	//@Override
	//public String toString(){
		String result = "";
		List<BacklogItemStatus> statusList = getStatusList();
		List<BacklogItem> selectedItems = getBacklogItemsForStatus(statusList, "Selected");
		List<BacklogItem> inProgressItems = getBacklogItemsForStatus(statusList, "In Progress");
		List<BacklogItem> impededItems = getBacklogItemsForStatus(statusList, "Impeded");
		List<BacklogItem> UncommittedItems= getBacklogItemsForStatus(statusList, "Uncommitted");
		
		List<Release> releaseList = getReleaseList();
		
		result += selectedReport(selectedItems,releaseList) +
				  inProgressReport(inProgressItems,releaseList) +
				  impededReport(impededItems,releaseList) +
				  umcommittedReport(UncommittedItems,releaseList);
			
		return result;
	}
		
		
	public String selectedReport(List<BacklogItem> selectedItems, List<Release> releaseList)
	{
		String report = "";
		Date today = new Date();
		List<Theme> themeList = new ArrayList<Theme>();
		
		report += "Workflow: Selected<ul>";
		
		for(BacklogItem b : selectedItems)
		{
			for(Release r : releaseList)
			{
				if(b.getReleaseId().equals(r.getId()) && r.getEndDate().before(today))
				{
					report += "<li>" + b.getName() + " is past release date. - Release Date: " + r.getEndDate().toString().replaceAll("00:00:00 CST ", "") +
							  " Release: " + r.getName() + " - Themes: ";
					themeList = b.getThemes();
					for(Theme t : themeList)
					{
						report += t.getName() + ", ";
					}
					report += "</li>" +"";
				}
				
				
				if(b.getReleaseId().equals(r.getId()) && (r.getStartDate().getTime() - today.getTime() / (24 * 60 * 60 * 1000)) > 5)
				{
					report += "<li>" + b.getName() + ", in workflow longer than specified (5) days. - Release Date: " + r.getEndDate().toString().replaceAll("00:00:00 CST ", "") +
							  " Release: " + r.getName() + " - Themes: ";
					themeList = b.getThemes();
					for(Theme t : themeList)
					{
						report += t.getName() + ", ";
					}
					report += "</li>" +"";
				}
				
			}
		}
		
		if(selectedItems.size() > 15)
			report += "Task Capacity Exceeded in Selected Workflow (max capacity currently set at 15)</ul>";
		else
			report += "</ul>";
		
		return report;
	}
	
	public String inProgressReport(List<BacklogItem> inProgressItems, List<Release> releaseList)
	{
		String report = "";
		Date today = new Date();
		List<Theme> themeList = new ArrayList<Theme>();
		
		report += "Workflow: In Progress<ul>";
		
		for(BacklogItem b : inProgressItems)
		{
			for(Release r : releaseList)
			{
				if(b.getReleaseId().equals(r.getId()) && r.getEndDate().before(today))
				{
					report += "<li>" + b.getName() + " is past release date. - Release Date: " + r.getEndDate().toString().replaceAll("00:00:00 CST ", "") +
							  " Release: " + r.getName() + " - Themes: ";
					themeList = b.getThemes();
					for(Theme t : themeList)
					{
						report += t.getName() + ", ";
					}
					report += "</li>" +"";
				}
				
				
				if(b.getReleaseId().equals(r.getId()) && (r.getStartDate().getTime() - today.getTime() / (24 * 60 * 60 * 1000)) > 10)
				{
					report += "<li>" + b.getName() + ", in workflow longer than specified (10) days. - Release Date: " + r.getEndDate().toString().replaceAll("00:00:00 CST ", "") +
							  " Release: " + r.getName() + " - Themes: ";
					themeList = b.getThemes();
					for(Theme t : themeList)
					{
						report += t.getName() + ", ";
					}
					report += "</li>" +"";
				}
				
			}
		}
		
		if(inProgressItems.size() > 15)
			report += "Task Capacity Exceeded in In Progress Workflow (max capacity currently set at 15)</ul>";
		else
			report += "</ul>";
		
		return report;
	}
	
	public String impededReport(List<BacklogItem> impededItems, List<Release> releaseList)
	{
		String report = "";
		Date today = new Date();
		List<Theme> themeList = new ArrayList<Theme>();
		
		report += "Workflow: Impeded<ul>";
		
		for(BacklogItem b : impededItems)
		{
			for(Release r : releaseList)
			{
				if(b.getReleaseId().equals(r.getId()) && r.getEndDate().before(today))
				{
					report += "<li>" + b.getName() + " is past release date. - Release Date: " + r.getEndDate().toString().replaceAll("00:00:00 CST ", "") +
							  " Release: " + r.getName() + " - Themes: ";
					themeList = b.getThemes();
					for(Theme t : themeList)
					{
						report += t.getName() + ", ";
					}
					report += "</li>" +"";
				}
				
				
				if(b.getReleaseId().equals(r.getId()) && (r.getStartDate().getTime() - today.getTime() / (24 * 60 * 60 * 1000)) > 5)
				{
					report += "<li>" + b.getName() + ", in workflow longer than specified (5) days. - Release Date: " + r.getEndDate().toString().replaceAll("00:00:00 CST ", "") +
							  " Release: " + r.getName() + " - Themes: ";
					themeList = b.getThemes();
					for(Theme t : themeList)
					{
						report += t.getName() + ", ";
					}
					report += "</li>" +"";
				}
				
			}
		}
		
		if(impededItems.size() > 5)
			report += "Task Capacity Exceeded in Impeded Workflow (max capacity currently set at 5)</ul>";
		else
			report += "</ul>";
		
		
		return report;
	}
	
	public String umcommittedReport(List<BacklogItem> UncommittedItems, List<Release> releaseList)
	{
		String report = "";
		Date today = new Date();
		
		report += "Workflow: Uncommitted<ul>";
		for(BacklogItem b : UncommittedItems)
		{
			for(Release r : releaseList)
			{
				if(b.getReleaseId().equals(r.getId()) && r.getEndDate().before(today) && r.isArchived() == false)
				{
					report += "<li>Verify release date for " + r.getName() + " since it has uncompleted tasks - Release date: " + r.getEndDate().toString().replaceAll("00:00:00 CST ", "") + "</li>";
				}
				
				if(b.getReleaseId().equals(r.getId()) && 
				  (r.getEndDate().getTime()/ (24 * 60 * 60 * 1000))  >  ((today.getTime()/ (24 * 60 * 60 * 1000)) + b.getEstimate()) )
				{
					report += "<li>Warning - Release " + r.getId().toString() + " - " + r.getName() + " has Uncommitted work that may not be completed - Release Date: " + r.getEndDate().toString().replaceAll("00:00:00 CST ", "") +"</li>";
				}
			}
		}
		report += "</ul>";
		return report;
	}
}



