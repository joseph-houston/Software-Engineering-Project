package com.cs4910.CBWebApp.Models;
import java.util.ArrayList;
import java.util.List;

import com.danube.scrumworks.api2.client.*;

public class PopulateUsers {

	API2SoapClient client = new API2SoapClient();
	ScrumWorksAPIService service = client.getAPIservice();
	
	public PopulateUsers(){
		
	}
	
	public List<User> getUsers(Product product){
		List<User> teamMembers = new ArrayList<User>();
		List<Long> teams = new ArrayList<Long>();
		
		try {
			teams = product.getTeamIds();
			for(int i = 0; i<teams.size();i++){
				teamMembers = service.getTeamMembers(teams.get(i));
				for(int j = 0; j <teamMembers.size(); j++){
					teamMembers.add(teamMembers.get(j));
				}
			}
		}
		catch (ScrumWorksException e) {
			e.printStackTrace();
		}
		
		return teamMembers;
	}
}
