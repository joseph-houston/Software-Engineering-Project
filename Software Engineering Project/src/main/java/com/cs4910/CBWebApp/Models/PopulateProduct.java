package com.cs4910.CBWebApp.Models;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.danube.scrumworks.api2.client.*;

public class PopulateProduct {
	public static void main(){
		try {
			API2SoapClient client = new API2SoapClient();
			ScrumWorksAPIService service = client.getAPIservice();
			List<Product> products = new ArrayList<Product>();
			products = service.getProducts();
			List<Product> kanbanProducts = new ArrayList<Product>();
			List<Long> teams = new ArrayList<Long>();
			for(int i = 0; i<products.size();i++){
				teams = products.get(i).getTeamIds();
				Team team = service.getTeamById(teams.get(0));
				System.out.print(team.getType());
				if(team.getType() == "Sprints"){
					kanbanProducts.add(products.get(i));
					System.out.println(products.get(i).toString());
				};
			}
			System.out.println(kanbanProducts.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
