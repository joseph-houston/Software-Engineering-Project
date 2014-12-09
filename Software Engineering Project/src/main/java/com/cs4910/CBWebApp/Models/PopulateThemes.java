package com.cs4910.CBWebApp.Models;
import java.util.ArrayList;
import java.util.List;

import com.danube.scrumworks.api2.client.*;

public class PopulateThemes {

	API2SoapClient client = new API2SoapClient();
	ScrumWorksAPIService service = client.getAPIservice();
	
	public PopulateThemes(){
	}
	
	public List<Theme> getThemes(Product product) throws ScrumWorksException{
		List<Theme> themes = new ArrayList<Theme>();
		themes = service.getThemesForProduct(product.getId());
		
		return themes;
	}
}