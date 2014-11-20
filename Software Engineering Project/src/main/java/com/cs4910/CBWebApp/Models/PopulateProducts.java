package com.cs4910.CBWebApp.Models;

import java.util.ArrayList;
import java.util.List;

import com.danube.scrumworks.api2.client.*;

public class PopulateProducts {

	API2SoapClient client = new API2SoapClient();
	ScrumWorksAPIService service = client.getAPIservice();
	
	public static void main(){
		PopulateProducts productList = new PopulateProducts();
		List<Product> products = new ArrayList<Product>();
		products = productList.getAllProducts();
		System.out.println("Testing client");
		System.out.println(products.toString());
	}
	
	public PopulateProducts(){
	}
	
	public List<Product> getKanbanProducts(){
		List<Product> products = new ArrayList<Product>();
		List<Product> kanbanProducts = new ArrayList<Product>();
		List<Long> teams = new ArrayList<Long>();
		
		try {
			products = service.getProducts();
			for(int i = 0; i<products.size();i++){
				teams = products.get(i).getTeamIds();
				Team team = service.getTeamById(teams.get(0));
				System.out.print(team.getType());
				if(team.getType().equals("Kanban")){
					kanbanProducts.add(products.get(i));
				}
			}
		}
		
		catch (ScrumWorksException e) {
			e.printStackTrace();
		}
		
		return kanbanProducts;
	}
	
	public List<Product> getSprintProducts(){
		List<Product> products = new ArrayList<Product>();
		List<Product> sprintProducts = new ArrayList<Product>();
		List<Long> teams = new ArrayList<Long>();
		
		try {
			products = service.getProducts();
			for(int i = 0; i<products.size();i++){
				teams = products.get(i).getTeamIds();
				Team team = service.getTeamById(teams.get(0));
				System.out.print(team.getType());
				if(team.getType().equals("Sprints")){
					sprintProducts.add(products.get(i));
				}
			}
		}
		catch (ScrumWorksException e) {
			e.printStackTrace();
		}
		
		return sprintProducts;
	}
	
	public List<Product> getAllProducts(){
		List<Product> products = new ArrayList<Product>();
		try {
			products = service.getProducts();
		}
		catch (ScrumWorksException e) {
			e.printStackTrace();
		}
		return products;
	}
	
}
