package com.cs4910.CBWebApp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This controller uses class views to render pdf documents. Thes class view are found in 
 * the view package. 
 * 
 * @author Njenga Gathee
 *
 */


@Controller
public class PdfController {
	/**
	 * This will map kanban workflow warnings report. It will require the product name 
	 * to create the report and display it as a pdf document.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/report.pdf", method = RequestMethod.GET)
	 ModelAndView kanbanWorkflowWarningsPdf(HttpServletRequest request,
	   HttpServletResponse response) throws Exception {
		  System.out.println("Calling generatePdf()...");
		  String testStr = request.getParameter("productName");
		    
		  ModelAndView modelAndView = new ModelAndView("kanbanWorkflowWarningsReportPdf", "command",testStr);
		  return modelAndView;
	 }
	 
	 /**
	  * This will map kanban activity report and will require all the parameters passed by the 
	  * the report generation form to generate the report and display it as a pdf document. 
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/kanbanActivityReport/report.pdf", method = RequestMethod.GET)
	 ModelAndView kanbanActivityReportPdf(HttpServletRequest request,
	   HttpServletResponse response) throws Exception {
		  System.out.println("Calling generatePdf()...");
		  String testStr = request.getParameter("productName"); 
		  ModelAndView modelAndView = new ModelAndView("kanbanActivityReportPdf", "command",testStr);
		  return modelAndView;
	 }	 
	 
	 /**
	  * This will map user activity report and will require all the parameters passed by the 
	  * report generation form to generate and display the report as a pdf document.
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/userActivityReport/report.pdf", method = RequestMethod.GET)
	 ModelAndView kanbanUserActivityReportPdf(HttpServletRequest request,
	   HttpServletResponse response) throws Exception {
		  System.out.println("Calling generatePdf()...");  
		  String testStr = request.getParameter("productName");		  
		  ModelAndView modelAndView = new ModelAndView("kanbanUserActivityReportPdf", "command", testStr);	  
		  return modelAndView;
	 }	 	 
}
