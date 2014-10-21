package com.cs4910.CBWebApp;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		String title = "Kanban Workflow Warnings";
		String reportType = "kanban-workflow-warnings";
		
		model.addAttribute("title", title);
		model.addAttribute("reportType", reportType);

		// now we call the class for this report and process what to return
		String reportData = "Data from KanbanWorkflowWarnings model";
		model.addAttribute("reportData", reportData);		
		
		return "home";
	}
	
	@RequestMapping(value = "/kanbanActivityReport", method = RequestMethod.GET)
	public String kanbanActivityReport(Locale locale, Model model) {
		String title = "Kanban Activity Report";
		String reportType = "kanban-activity-report";
		
		model.addAttribute("title", title);
		model.addAttribute("reportType", reportType);

		// now we call the class for this report and process what to return
		String reportData = "Data from KanbanActivityReport model";
		model.addAttribute("reportData", reportData);
		
		return "home";
	}	

	@RequestMapping(value = "/userActivityReport", method = RequestMethod.GET)
	public String userActivityReport(Locale locale, Model model) {
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
