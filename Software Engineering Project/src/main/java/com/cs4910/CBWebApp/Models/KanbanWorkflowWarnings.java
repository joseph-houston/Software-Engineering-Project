package com.cs4910.CBWebApp.Models;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.danube.scrumworks.api2.client.*;


public class KanbanWorkflowWarnings {
	private API2SoapClient client = new API2SoapClient();
	private ScrumWorksAPIService apiService = client.getAPIservice();
	private Product selectedProduct;
	
	
	public KanbanWorkflowWarnings(String productName) {
		super();
		try {
			this.selectedProduct = apiService.getProductByName(productName);
		} catch (ScrumWorksException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	/***
	 * To test this, we need to add a lot of items in each workflow, maybe vary their
	 * release dates and count how many backlog items belong to each custom status for 
	 * for the warning condition. Figure out how to get themes for backlog items and also
	 * calculate days in the number of days in the releases 
	 * @return
	 * @throws ScrumWorksException
	 */
	public String display() throws ScrumWorksException {
		String result = "";
		List<BacklogItemStatus> customStatuses = apiService.getCustomBacklogItemStatuses(selectedProduct.getId());
		List<BacklogItem> backlogItems = apiService.getBacklogItemsInProduct(selectedProduct.getId(), false);
		List<Release> releasesForProduct = apiService.getReleasesForProduct(selectedProduct.getId());
		//List<Theme> themesForProduct = apiService.getThemesForProduct(selectedProduct.getId());
		List<Task> tasksForProduct = apiService.getTasksForProduct(selectedProduct.getId());
		
		Date today = new Date();
		String releaseName;
		Date releaseDate = new Date();
		int numSelected = 0, numInProgress = 0, numImpeded = 0, numUncommited = 0;
		
		for(BacklogItemStatus s : customStatuses) {	
			if(s.getName().equalsIgnoreCase("done") || s.getName().equalsIgnoreCase("not started")){
				result += "";
			} else {
				result += "<h4>Workflow: " + s.getName() + "</h4>";	
				for(BacklogItem b : backlogItems){
					if(b.getStatusId() == s.getId()){
						if(s.getName().equalsIgnoreCase("selected"))
							numSelected += 1;
						if(s.getName().equalsIgnoreCase("in progress"))
							numInProgress += 1;
						if(s.getName().equalsIgnoreCase("impeded"))
							numImpeded += 1;
						if(s.getName().equalsIgnoreCase("uncommited"))
							numUncommited += 1;												
						for(Release r : releasesForProduct){
							if(r.getId().equals(b.getReleaseId())){
								releaseName = r.getName();
								releaseDate = r.getEndDate();
								if(releaseDate.before(today)){
									result +=  b.getName() + " is past release date - ";
									result +="Release Date: " + releaseDate + " Release: " + releaseName + " Theme(s): ";
									if(b.getThemes().size() == 0)
										result += "None";
									for(Theme t : b.getThemes()) {
										result += t.getName() + ", ";
									}									
								}
							}	
						}

						//result += " Date Request: " + b.;
						result += "<br>";
					}
				}

				if(s.getName().equalsIgnoreCase("selected") && numSelected > 2)
					result +=  "<span class='text-warning'>WARNING</span> - Capacity exceeded in Selected Workflow(max capacity currently set at 15).";
				if(s.getName().equalsIgnoreCase("in progress") && numInProgress > 4)
					result +=  "<span class='text-warning'>WARNING</span> - Capacity exceeded in In Progress Workflow(max capacity currently set at 15).";
				if(s.getName().equalsIgnoreCase("impeded") && numImpeded > 2)
					result +=  "<span class='text-warning'>WARNING</span> - Capacity exceeded in Impeded Workflow(max capacity currently set at 5). " + numImpeded;
				if(s.getName().equalsIgnoreCase("uncommited") && numUncommited > 4)
					result +=  "<span class='text-warning'>WARNING</span> - Capacity exceeded: ";			
				
				if(s.getName().equalsIgnoreCase("selected") && numSelected == 0)
					result +=  "Nothing to show.";
				if(s.getName().equalsIgnoreCase("in progress") && numInProgress == 0)
					result +=  "Nothing to show.";
				if(s.getName().equalsIgnoreCase("impeded") && numImpeded == 0)
					result +=  "Nothing to show.";
				if(s.getName().equalsIgnoreCase("uncommited") && numUncommited == 0)
					result +=  "Nothing to show.";					
			}

			
			/* for now...
			result += "<ul>";
			result += "<li>";
				result += "Past release date<br>";
				result += "Release Name - Themes: ";
			result += "</li>";
			
			result += "<li>";
				result += "In workflow for x days <br>";
				result += "Release Name - Themes: ";
			result += "</li>";			
			
			result += "<li>";
				result += "<span class='text-warning'>WARNING</span> - Capacity exceeded";
			result += "</li>";			
			result += "</ul>";
			*/
			System.out.println(s.getName());
		}
		System.out.println();
		return result;
	}
		
		
		
		
		/*
		public void KanbanWorkflowWarningForm(Product selectedProduct)
		{
			this.selectedProduct = selectedProduct;
		}
		
		@Override
		public String toString()
		{
			API2SoapClient client = new API2SoapClient();
			ScrumWorksAPIService apiService = client.getAPIservice();
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
		
		}
		*/
}



