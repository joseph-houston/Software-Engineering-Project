package com.cs4910.CBWebApp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cs4910.CBWebApp.domain.DomainEmployee;

@Controller
public class PdfController {
	 @RequestMapping(value = "/kanbanWorkflowWarnings.pdf", method = RequestMethod.GET)
	 ModelAndView kanbanWorkflowWarningsPdf(HttpServletRequest request,
	   HttpServletResponse response) throws Exception {
		  System.out.println("Calling generatePdf()...");
		  
		  DomainEmployee employee = new DomainEmployee();
		  employee.setFirstName("George");
		  employee.setLastName("Gathee");
		  
		  ModelAndView modelAndView = new ModelAndView("kanbanWorkflowWarningsReportPdf", "command",employee);
		  
		  return modelAndView;
	 }
	 
	 @RequestMapping(value = "/kanbanActivityReport.pdf", method = RequestMethod.GET)
	 ModelAndView kanbanActivityReportPdf(HttpServletRequest request,
	   HttpServletResponse response) throws Exception {
		  System.out.println("Calling generatePdf()...");
		  
		  DomainEmployee employee = new DomainEmployee();
		  employee.setFirstName("George");
		  employee.setLastName("Gathee");
		  
		  ModelAndView modelAndView = new ModelAndView("kanbanActivityReportPdf", "command",employee);
		  
		  return modelAndView;
	 }	 
	 
	 @RequestMapping(value = "/kanbanUserActivityReport.pdf", method = RequestMethod.GET)
	 ModelAndView kanbanUserActivityReportPdf(HttpServletRequest request,
	   HttpServletResponse response) throws Exception {
		  System.out.println("Calling generatePdf()...");
		  
		  DomainEmployee employee = new DomainEmployee();
		  employee.setFirstName("George");
		  employee.setLastName("Gathee");
		  
		  ModelAndView modelAndView = new ModelAndView("kanbanUserActivityReportPdf", "command",employee);
		  
		  return modelAndView;
	 }	 	 
}
