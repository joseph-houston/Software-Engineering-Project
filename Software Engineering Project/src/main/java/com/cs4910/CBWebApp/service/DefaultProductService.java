/**
 * 
 */
package com.cs4910.CBWebApp.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.cs4910.CBWebApp.domain.DomainProduct;
import com.cs4910.CBWebApp.domain.DomainTheme;
import com.cs4910.CBWebApp.domain.DomainUser;
import com.cs4910.CBWebApp.Models.*;
import com.danube.scrumworks.api2.client.*;

public class DefaultProductService implements ProductService {
	private Map<String, DomainProduct> products = new LinkedHashMap<String, DomainProduct>();
	
	//default will be all products
	public DefaultProductService(){
		super();
		PopulateProducts allProducts = new PopulateProducts();
		PopulateUsers users = new PopulateUsers();
		PopulateThemes themes = new PopulateThemes();
		List<Product> listProducts = allProducts.getAllProducts();

		for(int i = 0; i < listProducts.size(); i++){
		
			Product product = listProducts.get(i);
			DomainProduct domainProd = new DomainProduct(product.getName());
			List<User> productUsers = users.getUsers(product);
			List<Theme> productThemes;
			try {
				productThemes = themes.getThemes(product);
			
			
				for(int j = 0; j<productUsers.size(); j++){
					domainProd.addUser(productUsers.get(j).getUserName());
				}
			
				for(int k = 0; k<productThemes.size(); k++){
					domainProd.addTheme(productThemes.get(k).getName(), productThemes.get(k).getId());
				}

				this.products.put(domainProd.getName(), domainProd);
			
			} catch (ScrumWorksException e) {
				e.printStackTrace();
			}
		}
	}	
	
	//default will be all kanban products
		public DefaultProductService(boolean kanban){
			super();
			PopulateProducts kanbanProducts = new PopulateProducts();
			PopulateUsers users = new PopulateUsers();
			PopulateThemes themes = new PopulateThemes();
			List<Product> listProducts = kanbanProducts.getKanbanProducts();

			for(int i = 0; i < listProducts.size(); i++){
				
				Product product = listProducts.get(i);
				DomainProduct domainProd = new DomainProduct(product.getName());
				List<User> productUsers = users.getUsers(product);
				List<Theme> productThemes;
				try {
					productThemes = themes.getThemes(product);
				
				
					for(int j = 0; j<productUsers.size(); j++){
						domainProd.addUser(productUsers.get(j).getUserName());
					}
				
					for(int k = 0; k<productThemes.size(); k++){
						domainProd.addTheme(productThemes.get(k).getName(), productThemes.get(k).getId());
					}

					this.products.put(domainProd.getName(), domainProd);
				} catch (ScrumWorksException e) {
					e.printStackTrace();
				}
			}
		}

	@Override
	public Set<DomainProduct> findAllProducts() {
		Set<DomainProduct> set = new TreeSet<DomainProduct>();
		set.addAll(this.products.values());
		return set;
	}
	
	@Override
	public Set<DomainProduct> findKanbanProducts() {
		Set<DomainProduct> set = new TreeSet<DomainProduct>();
		set.addAll(this.products.values());
		return set;
	}

	@Override
	public Set<DomainUser> findAllUsersForProduct(String productName) {
		DomainProduct product = this.products.get(productName);
		return product.getUsers();
	}

	@Override
	public Set<DomainTheme> findAllThemesForProduct(String productName) {
		DomainProduct product = this.products.get(productName);
		return product.getThemes();
	}
	

}
