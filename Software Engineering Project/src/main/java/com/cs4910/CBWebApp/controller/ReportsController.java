package com.cs4910.CBWebApp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs4910.CBWebApp.Models.API2SoapClient;
import com.cs4910.CBWebApp.Models.KanbanWorkflowWarningsReport;
import com.cs4910.CBWebApp.Models.KanbanWorkflowWarnings;
import com.cs4910.CBWebApp.Models.PopulateProducts;
import com.cs4910.CBWebApp.Models.ReportScheduler;
import com.cs4910.CBWebApp.domain.DomainProduct;
import com.cs4910.CBWebApp.domain.DomainTheme;
import com.cs4910.CBWebApp.domain.DomainUser;
import com.cs4910.CBWebApp.service.DefaultProductService;
import com.danube.scrumworks.api2.client.*;


/**
 * @author Njenga Gathee
 * 
 * This Controller is a backend controller. It handles all the request made vial ajax on the 
 * report page.
 */
@Controller
public class ReportsController {
	private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);
	 
	private DefaultProductService productService = new DefaultProductService();
	private DefaultProductService kanbanProductService = new DefaultProductService(true);

	
	/**
	 * This maps an Ajax request for finding users. 
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody
	Set<DomainUser> usersForProduct(@RequestParam(value = "productName", required = true) String product) {
		logger.debug("finding users for product " + product);
		System.out.println("n users: " + kanbanProductService.findAllUsersForProduct(product).size());
		System.out.println("n themes: " + kanbanProductService.findAllThemesForProduct(product).size());		
		return this.kanbanProductService.findAllUsersForProduct(product);
	}	

	/**
	 * This maps an Ajax request for finding themes.
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/themes", method = RequestMethod.GET)
	public @ResponseBody
	Set<DomainTheme> themesForProduct(@RequestParam(value = "productName", required = true) String product) {
		logger.debug("finding themes for product " + product);
		System.out.println("n users: " + productService.findAllUsersForProduct(product).size());
		System.out.println("n themes: " + productService.findAllThemesForProduct(product).size());
		return this.productService.findAllThemesForProduct(product);
	}	

	/**
	 * This maps an Ajax request for finding all products.
	 * @return
	 */
	@RequestMapping(value = "/allProducts", method = RequestMethod.GET)
	public @ResponseBody
	Set<DomainProduct> findAllProducts() {
		System.out.println("Finding all products.");
		return this.productService.findAllProducts();
	}

	/**
	 * This maps an Ajax request for finding kanban products.
	 * @return
	 */
	@RequestMapping(value = "/kanbanProducts", method = RequestMethod.GET)
	public @ResponseBody
	Set<DomainProduct> findKanbanProducts() {
		System.out.println("Finding kanban products.");
		return this.kanbanProductService.findAllProducts();
	}	
		
	/**
	 * This maps an Ajax request for return kanban workflow warning report.
	 * @return
	 * @throws ScrumWorksException 
	 */
	@RequestMapping(value = "/getKanbanWorkflowWarningsReport", method = RequestMethod.GET)
	public @ResponseBody
	String getKanbanWorkflowWarningsReport(@RequestParam String productName) throws ScrumWorksException {
		logger.debug("Generating Kanban Workflow Warnings Report");
		// This should return json object 
		// Test your report generation here and print your outputs to console for now.
		API2SoapClient client = new API2SoapClient();
		ScrumWorksAPIService service = client.getAPIservice();	
		//Product selectedProduct = service.getProductByName(productName);
		
		//List<BacklogItem> backlogItems = service.getBacklogItemsInProduct(, false);
		
		//PopulateProducts productList = new PopulateProducts();
		//List<Product> products = new ArrayList<Product>();
		//products = productList.getKanbanProducts();
		//System.out.println("Testing client");
		//for(int j = 0; j < products.size(); j++){
			//System.out.println("Product Name: " + products.get(j).getName() + " ID:" + products.get(j).getId() + "Team Ids : " + products.get(j).getTeamIds());
			//System.out.println("----------------------------------------------------------------------");
			//List<BacklogItem> backlogItems = service.getBacklogItemsInProduct(products.get(j).getId(), false);
			//for (BacklogItem b : backlogItems) {
				//System.out.println("BacklogItem Name: " + b.getName());
				//List<BacklogItem> backlogItems = service.getBacklogItemsInRelease(r.getId(), false);
				//for (BacklogItem b : backlogItems){
				//	System.out.println("BacklogItem: " + b.getName() + " Id: " + b.getId());
				//}
				
			//}
			
		//	List<BacklogItemStatus> status = service.getCustomBacklogItemStatuses(products.get(j).getId());
			//List<Task> tasks = service.getTasks(products.get(j).getId());
		//	for (BacklogItemStatus s : status) {
		//		System.out.println("Custom BacklogItem: " + s.getName() + " Id: " + s.getId() + " Type: " + s.getType());
				//for (Task t : tasks){
				//	System.out.println("Task: " + t.getName() + " Id: " + t.getId() + " " + t.getStatus());
		//		//}
		//	}
			//List<BacklogItem> backlogItems = service.getBacklogItemsInProduct(products.get(j).getId(), false);
			
			//for(int k =0; k < backlogItems.size(); k++){
			//	System.out.println("Backlog Item Name: " + backlogItems.get(k).getName() + " Id: " + backlogItems.get(k).getId());
			//	List<Task> tasks = service.getTasks(backlogItems.get(k).getId());
			//	for(int i = 0; i < tasks.size(); i++){
			//		System.out.println("Task Name: " + tasks.get(i).getName() + " Status: " + tasks.get(i).getStatus());
			//	}
			//}
			//System.out.println();
		//}

		KanbanWorkflowWarnings report = new KanbanWorkflowWarnings(productName);
		String test = report.displayReport();
		
		
		return test;
	}
	
	/**
	 * This maps an Ajax request for return kanban activity report.
	 * @return
	 */
	@RequestMapping(value = "/getKanbanActivityReport", method = RequestMethod.GET)
	public @ResponseBody
	String getKanbanActivityReport(@RequestParam(value = "productName", required = true) String productName,
			@RequestParam(value = "themeValues", required = true) String[] themes,
			@RequestParam(value = "includeHistory", required = false) boolean includeHistory,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) {
		logger.debug("Generating Kanban Activity Report");
		
		// Test your report generation here and print your outputs to console for now.
		
				
		
		return "Data submited: " + productName + " Themes: " + themes[0] + " Include History: " +
				includeHistory + " Start Date: " + startDate + " End Date: " + endDate;
	}	

	/**
	 * This maps an Ajax request for return kanban activity report.
	 * @return
	 */
	@RequestMapping(value = "/getUserActivityReport", method = RequestMethod.GET)
	public @ResponseBody
	String getUserActivityReport(@RequestParam(value = "productName", required = true) String productName,
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "includeDetails", required = false) boolean includeDetails,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) {
		logger.debug("Generating User Activity Report");
		
		// Test your report generation here and print your outputs to console for now.
		
		
		return "Data submited: " + productName + " User Name: " + userName + " Include History: " +
				includeDetails + " Start Date: " + startDate + " End Date: " + endDate;
	}		
	
	/**
	 * This will map the Ajax request for scheduleEmail form and will get the product name and 
	 * the email and save them to a file for use by monitor which will create reports based on
	 * the product name and send those reports to the specified email.
	 * @param product
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/scheduleEmail", method = RequestMethod.POST)
	public @ResponseBody String scheduleEmail(@RequestParam String product, @RequestParam String email,
										HttpServletRequest request) throws IOException{
		String result = "Product: " + product + " Email: " + email;
		System.out.println(result);
		String dataPath  = request.getSession().getServletContext().getRealPath("/WEB-INF/data/emails.dat");
		
		System.out.println(dataPath);
		String record = product + ":" + email;
		
		ReportScheduler.storeScheduleRecord(dataPath, record);
		ReportScheduler.readScheduleRecords(dataPath);

		return result;
	}
	
	
}
