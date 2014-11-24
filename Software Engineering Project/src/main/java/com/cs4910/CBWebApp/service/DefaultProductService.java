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
import com.cs4910.CBWebApp.domain.Theme;
import com.cs4910.CBWebApp.domain.User;
import com.cs4910.CBWebApp.Models.*;
import com.danube.scrumworks.api2.client.Product;

public class DefaultProductService implements ProductService {
	private Map<String, DomainProduct> products = new LinkedHashMap<String, DomainProduct>();
	
	//default will be all products
	public DefaultProductService() {
		super();
		PopulateProducts allProducts = new PopulateProducts();
		List<Product> listProducts = allProducts.getAllProducts();

		int i = 0;
		
		Product product = listProducts.get(i);
		DomainProduct domainProd = new DomainProduct(product.getName());
		domainProd.addUser("product1 user1").addUser("product1 user2").addUser("product1 user3");
		domainProd.addTheme("product1 theme1").addTheme("product1 theme2").addTheme("product1 theme3").addTheme("product1 theme4");
		this.products.put(domainProd.getName(), domainProd);
		
//		product = new DomainProduct("product 2");
//		product.addUser("product2 user1").addUser("product2 user2").addUser("product2 user3");
//		product.addTheme("product2 theme1").addTheme("product2 theme2").addTheme("product2 theme2");
//		this.products.put(product.getName(), product);
//		
//		product = new DomainProduct("product 3");
//		product.addUser("product3 user1");
//		product.addTheme("product3 theme1");
//		this.products.put(product.getName(), product);
//		
//		product = new DomainProduct("product 4");
//		product.addUser("product4 user1");
//		this.products.put(product.getName(), product);
//		
//		product = new DomainProduct("product 5");
//		product.addTheme("product5 theme1");
//		this.products.put(product.getName(), product);
//		
//		product = new DomainProduct("product 6");
//		this.products.put(product.getName(), product);
	}	
	
	//default will be all kanban products
		public DefaultProductService(boolean kanban) {
			super();
			PopulateProducts allProducts = new PopulateProducts();
			List<Product> listProducts = allProducts.getAllProducts();

			int i = 0;
			
			Product product = listProducts.get(i);
			DomainProduct domainProd = new DomainProduct(product.getName());
			domainProd.addUser("product1 user1").addUser("product1 user2").addUser("product1 user3");
			domainProd.addTheme("product1 theme1").addTheme("product1 theme2").addTheme("product1 theme3").addTheme("product1 theme4");
			this.products.put(domainProd.getName(), domainProd);
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
	public Set<User> findAllUsersForProduct(String productName) {
		DomainProduct product = this.products.get(productName);
		return product.getUsers();
	}

	@Override
	public Set<Theme> findAllThemesForProduct(String productName) {
		DomainProduct product = this.products.get(productName);
		return product.getThemes();
	}
	

}
