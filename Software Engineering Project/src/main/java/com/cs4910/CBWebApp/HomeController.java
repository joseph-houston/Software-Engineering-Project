package com.cs4910.CBWebApp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.danube.scrumworks.api2.client.ScrumWorksException;


/**
 * Handles requests for the application home page. This controller just return a home
 * page view. The view has a logic to display the three different reports depending on 
 * the action mapped and also the string 'reportType'. It also sets the title and report 
 * type.
 */
@Controller
public class HomeController {
	/**
	 * This will map kanban workflow warnings url and which is the root and also display
	 * the report.
	 * @throws ScrumWorksException 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home( Model model) throws ScrumWorksException {
		String title = "Kanban Workflow Warnings";
		String reportType = "kanban-workflow-warnings";
		
		model.addAttribute("title", title);
		model.addAttribute("reportType", reportType);

		// now we call the class for this report and process what to return
		//String reportData = "Data from KanbanWorkflowWarnings model";
		///model.addAttribute("reportData", reportData);	
		
		// this is an example to call the backend objects and also display the 
		// the activities on the console. You can also pass whatever data you want to 
		// model and display the same on the report page. For example see how title 
		// is being passed to model and see how it is being parsed on home.jsp
		/*
		API2SoapClient client = new API2SoapClient();
		ScrumWorksAPIService service = client.getAPIservice();
		List<Product> products = new ArrayList<Product>();
		products = service.getProducts();
		System.out.println("Testing client");
		System.out.println(products.get(0).getName().toString());
		System.out.println(products.get(0).getId().toString());
		*/
		return "home";		
	}
	
	/**
	 * This action will map kanban activity report url and there will use the
	 * logic on home view to display kanban activity report.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/kanbanActivityReport", method = RequestMethod.GET)
	public String kanbanActivityReport( Model model) {
		String title = "Kanban Activity Report";
		String reportType = "kanban-activity-report";
		
		model.addAttribute("title", title);
		model.addAttribute("reportType", reportType);

		// now we call the class for this report and process what to return
		String reportData = "Data from KanbanActivityReport model";
		model.addAttribute("reportData", reportData);
		
		return "home";
	}
	
	/**
	 * This will map user activity report url and will display the report.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/userActivityReport", method = RequestMethod.GET)
	public String userActivityReport(Model model) {
		String title = "User Activity Report";
		String reportType = "user-activity-report";
		
		model.addAttribute("title", title);
		model.addAttribute("reportType", reportType);
		
		// now we call the class for this report and process what to return
		String reportData = "Data from UserActivityReport model";
		model.addAttribute("reportData", reportData);
		return "home";
	}
	
}
