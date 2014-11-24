package com.cs4910.CBWebApp.controller;

import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs4910.CBWebApp.domain.DomainProduct;
import com.cs4910.CBWebApp.domain.DomainTheme;
import com.cs4910.CBWebApp.domain.DomainUser;
import com.cs4910.CBWebApp.service.DefaultProductService;

@Controller
public class ReportsController {
	private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);
	 
	private DefaultProductService productService = new DefaultProductService();
	
	private DefaultProductService kanbanProductService = new DefaultProductService(true);
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody
	Set<DomainUser> usersForProduct(@RequestParam(value = "productName", required = true) String product) {
		logger.debug("finding users for product " + product);
		System.out.println("n users: " + productService.findAllUsersForProduct(product).size());
		System.out.println("n themes: " + productService.findAllThemesForProduct(product).size());		
		return this.productService.findAllUsersForProduct(product);
	}	

	@RequestMapping(value = "/themes", method = RequestMethod.GET)
	public @ResponseBody
	Set<DomainTheme> themesForProduct(@RequestParam(value = "productName", required = true) String product) {
		logger.debug("finding themes for product " + product);
		System.out.println("n users: " + productService.findAllUsersForProduct(product).size());
		System.out.println("n themes: " + productService.findAllThemesForProduct(product).size());
		return this.productService.findAllThemesForProduct(product);
	}	

	@RequestMapping(value = "/kanbanProducts", method = RequestMethod.GET)
	public @ResponseBody
	Set<DomainProduct> kanbanProducts() {
		logger.debug("finding all products");
		return this.productService.findAllProducts();
	}	
	
	@RequestMapping(value = "/allProducts", method = RequestMethod.GET)
	public @ResponseBody
	Set<DomainProduct> allProducts() {
		logger.debug("finding all products");
		return this.productService.findAllProducts();
	}	
	
}
