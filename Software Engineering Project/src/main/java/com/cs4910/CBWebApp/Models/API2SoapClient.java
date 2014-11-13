package com.cs4910.CBWebApp.Models;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.danube.scrumworks.api2.ScrumWorksService;
import com.danube.scrumworks.api2.client.*;

/**
 * API2SoapClient is a convenience class for calling Scrumworks web services.
 * 
 * @author jhouston
 */

public class API2SoapClient {
	protected URL url;
	
	protected ScrumWorksAPIService apiService;
	
	protected static String DEFAULT_URL = "http://localhost:8080/scrumworks/login";
	
	protected static String DEFAULT_USER = "administrator";

	protected static String DEFAULT_PWD = "password";
	
	protected Product product;
	
	protected static String DEFAULT_EFFORT_UNITS = "Perfect Days";

	protected static Long DEFAULT_PRODUCT_ID = new Long(-162609799794753328l);

	protected static String DEFAULT_PRODUCT_NAME = "Development Services";
	
	public API2SoapClient() {
		super();
		initialize();
	}
	
	public static void main() {
		try {
			API2SoapClient client = new API2SoapClient();
			Product product = null;
			product = client.getProduct();
			System.out.println("Testing client");
			System.out.println(product.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	protected void initialize() {
		try {
			ScrumWorksAPIService service = ScrumWorksService.getConnection(DEFAULT_URL, DEFAULT_USER, DEFAULT_PWD);
			setAPIservice(service);
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}

	}
	
	public ScrumWorksAPIService getAPIservice() {
		return apiService;
	}

	public void setAPIservice(ScrumWorksAPIService apiService) {
		this.apiService = apiService;
	}
	
	public Product getProduct() {
		if (product == null) {
			Product product = new Product();
			product.setEffortUnits(DEFAULT_EFFORT_UNITS);
			product.setId(DEFAULT_PRODUCT_ID);
			product.setName(DEFAULT_PRODUCT_NAME);
			setProduct(product);
		}
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
