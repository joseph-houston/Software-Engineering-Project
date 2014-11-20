package com.cs4910.CBWebApp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.cs4910.CBWebApp.Models.API2SoapClient;
import com.cs4910.CBWebApp.Models.PopulateProducts;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home( Model model) {
		String title = "Kanban Workflow Warnings";
		String reportType = "kanban-workflow-warnings";
		
		model.addAttribute("title", title);
		model.addAttribute("reportType", reportType);

		// now we call the class for this report and process what to return
		String reportData = "Data from KanbanWorkflowWarnings model";
		model.addAttribute("reportData", reportData);		
		return "home";		

	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody String showWarningReport(@RequestParam String Product_Name, @RequestParam int Product_Value ){
		return "Product Name:" + Product_Name + " Product Value: " + Product_Value;
	}
	
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
	@RequestMapping(value = "/kanbanActivityReport", method = RequestMethod.POST)
	public @ResponseBody String populateThemes(@RequestParam String productName,  WebRequest webRequest, Model model){
		
		String selectData = "<option value=\"1\">Theme 1</option>";
		
        if (AjaxUtils.isAjaxRequest(webRequest)) {
            // prepare model for rendering success message in this request
            model.addAttribute("ajaxRequest", true);
            model.addAttribute("selectData", selectData);
            return null;
        }		
		return "home";
	}
	
	
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
