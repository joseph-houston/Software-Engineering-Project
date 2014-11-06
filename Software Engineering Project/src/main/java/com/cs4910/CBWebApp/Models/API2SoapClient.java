package com.cs4910.CBWebApp.Models;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.danube.scrumworks.api.client.ScrumWorksEndpoint;
import com.danube.scrumworks.api.client.ScrumWorksEndpointBindingStub;
import com.danube.scrumworks.api.client.ScrumWorksServiceLocator;
import com.danube.scrumworks.api2.*;
import com.danube.scrumworks.api2.client.*;

/**
 * API2SoapClient is a convenience class for calling Scrumworks web services.
 * 
 * @author jhouston
 */

public class API2SoapClient {
	protected URL url;
	
	protected ScrumWorksAPIService apiService;
	
	protected static String DEFAULT_URL = "http://localhost:8080/scrumworks/";
	
	protected static String DEFAULT_USER = "administrator";

	protected static String DEFAULT_PWD = "password";
	
	public API2SoapClient() {
		super();
		initialize();
	}
	
	protected void initialize() {
		try {
			setAPIservice(ScrumWorksService.getConnection(DEFAULT_URL, DEFAULT_USER, DEFAULT_PWD));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public ScrumWorksAPIService getAPIservice() {
		return apiService;
	}

	public void setAPIservice(ScrumWorksAPIService apiService) {
		this.apiService = apiService;
	}
	
}
