package com.cs4910.CBWebApp.controller;

import java.io.IOException;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs4910.CBWebApp.Models.ReportScheduler;
import com.cs4910.CBWebApp.domain.Product;
import com.cs4910.CBWebApp.domain.Theme;
import com.cs4910.CBWebApp.domain.User;
import com.cs4910.CBWebApp.service.ProductService;


/**
 * @author Njenga Gathee
 * 
 * This Controller is a backend controller. It handles all the request made vial ajax on the 
 * report page.
 */
@Controller
public class ReportsController {
	private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);
	
	// create an instance of ProductService type
	@Inject 
	private ProductService productService;
	
	/**
	 * This maps an Ajax request for finding users. 
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody
	Set<User> usersForProduct(@RequestParam(value = "productName", required = true) String product) {
		logger.debug("finding users for product " + product);
		System.out.println("n users: " + productService.findAllUsersForProduct(product).size());
		System.out.println("n themes: " + productService.findAllThemesForProduct(product).size());		
		return this.productService.findAllUsersForProduct(product);
	}	

	/**
	 * This maps an Ajax request for finding themes.
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/themes", method = RequestMethod.GET)
	public @ResponseBody
	Set<Theme> themesForProduct(@RequestParam(value = "productName", required = true) String product) {
		logger.debug("finding themes for product " + product);
		System.out.println("n users: " + productService.findAllUsersForProduct(product).size());
		System.out.println("n themes: " + productService.findAllThemesForProduct(product).size());
		return this.productService.findAllThemesForProduct(product);
	}	

	/**
	 * This maps an Ajax request for finding products.
	 * @return
	 */
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public @ResponseBody
	Set<Product> findAllStates() {
		logger.debug("finding all products");
		return this.productService.findAllProducts();
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
		
		String record = product + ":" + email;
		
		ReportScheduler.storeScheduleRecord(dataPath, record);
		ReportScheduler.readScheduleRecords(dataPath);

		return result;
	}
	
	
}
