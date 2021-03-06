package com.cs4910.CBWebApp.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs4910.CBWebApp.Models.KanbanActivityReport;
import com.cs4910.CBWebApp.Models.KanbanWorkflowWarnings;
import com.cs4910.CBWebApp.Models.ReportScheduler;
import com.cs4910.CBWebApp.Models.UserActivityReport;
import com.cs4910.CBWebApp.domain.DomainProduct;
import com.cs4910.CBWebApp.domain.DomainTheme;
import com.cs4910.CBWebApp.domain.DomainUser;
import com.cs4910.CBWebApp.service.DefaultProductService;
import com.cs4910.CBWebApp.service.Mailer;
import com.danube.scrumworks.api2.client.*;


/**
 * @author Njenga Gathee
 * 
 * This Controller is a backend controller. It handles all the request made via ajax on the 
 * report page.
 */
@Controller
public class ReportsController {
	private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);
	 
	private DefaultProductService productService = new DefaultProductService();
	private DefaultProductService kanbanProductService = new DefaultProductService(true);

    @Autowired
    private JavaMailSender mailSender;
	
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
		
		KanbanWorkflowWarnings report = new KanbanWorkflowWarnings(productName);
		return report.display();
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
		
				
		
		KanbanActivityReport kanbanReport = new KanbanActivityReport(productName, themes, includeHistory, startDate, endDate);
		return kanbanReport.toString();
	}	

	/**
	 * This maps an Ajax request for return User Activity Report.
	 * @return
	 */
	@RequestMapping(value = "/getUserActivityReport", method = RequestMethod.GET)
	public @ResponseBody
	String getUserActivityReport(@RequestParam(value = "productName", required = true) String productName,
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "includeDetails", required = true) boolean includeDetails,
			@RequestParam(value = "startDate", required = true) String startDate,
			@RequestParam(value = "endDate", required = true) String endDate) {
		logger.debug("Generating User Activity Report");
		if(startDate.equals("null")){
			return "Select start date";
		}
		
		// Test your report generation here and print your outputs to console for now.
		
		
		UserActivityReport userReport = new UserActivityReport(productName, userName, includeDetails, startDate, endDate);
		return userReport.toString();
	}		
	
	/**
	 * This will map the Ajax request for scheduleEmail form and will get the product name and 
	 * the email and save them to a file for use by monitor which will create reports based on
	 * the product name and send those reports to the specified email.
	 * @param product
	 * @param email
	 * @return
	 */
	@Autowired
	ServletContext servletContext;
	@RequestMapping(value = "/scheduleEmail", method = RequestMethod.POST)
	public @ResponseBody String scheduleEmail(@RequestParam String product, @RequestParam String email,
										HttpServletRequest request) throws IOException{
		String result = "Product: " + product + " Email: " + email;
		System.out.println(result);		
		File dataPath = new File( servletContext.getRealPath("/WEB-INF/data/emails.dat") );
		String record = product + ":" + email;
		
		ReportScheduler.storeScheduleRecord(dataPath, record);
		return result;
	}
	
	 //@Scheduled(cron="*/20 * * * * ?")
	 public void sendReport() throws ScrumWorksException, IOException {	
		 	List<String> scheduleRecords = new ArrayList<String>();
		 	File dataPath = new File( servletContext.getRealPath("/WEB-INF/data/emails.dat") );
		 	scheduleRecords = ReportScheduler.readScheduleRecords(dataPath);
		 	
		 	for(String s : scheduleRecords){
		 		String[] data = s.split(":");
		 		String productName = data[0];
		 		String recipientAddress = data[1];
				String subject = "Scheduled KanbanWorkflowWarings Report for - " + productName;
				KanbanWorkflowWarnings report = new KanbanWorkflowWarnings(productName);
				String message = report.display();
				SimpleMailMessage email = new SimpleMailMessage();
				email.setTo(recipientAddress);
				email.setSubject(subject);
				email.setText(message);
				 
				System.out.println("To: " + recipientAddress);
				System.out.println("Subject: " + subject);
				System.out.println("Message: " + message);				
				
				// sends the e-mail
				mailSender.send(email);				
		 	}		 	 
	 }

	
	
}
