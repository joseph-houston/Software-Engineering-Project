/**
 * 
 */
package com.cs4910.CBWebApp.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.cs4910.CBWebApp.domain.Product;
import com.cs4910.CBWebApp.domain.Theme;
import com.cs4910.CBWebApp.domain.User;

@Service
public class DefaultProductService implements ProductService {
	private Map<String, Product> products = new LinkedHashMap<String, Product>();
	
	public DefaultProductService() {
		super();
		Product product = new Product("product 1");
		product.addUser("product1 user1").addUser("product1 user2").addUser("product1 user3");
		product.addTheme("product1 theme1").addTheme("product1 theme2").addTheme("product1 theme3").addTheme("product1 theme4");
		this.products.put(product.getName(), product);
		
		product = new Product("product 2");
		product.addUser("product2 user1").addUser("product2 user2").addUser("product2 user3");
		product.addTheme("product2 theme1").addTheme("product2 theme2").addTheme("product2 theme2");
		this.products.put(product.getName(), product);
		
		product = new Product("product 3");
		product.addUser("product3 user1");
		product.addTheme("product3 theme1");
		this.products.put(product.getName(), product);
		
		product = new Product("product 4");
		product.addUser("product4 user1");
		this.products.put(product.getName(), product);
		
		product = new Product("product 5");
		product.addTheme("product5 theme1");
		this.products.put(product.getName(), product);
		
		product = new Product("product 6");
		this.products.put(product.getName(), product);
	}	
	

	@Override
	public Set<Product> findAllProducts() {
		Set<Product> set = new TreeSet<Product>();
		set.addAll(this.products.values());
		return set;
	}

	@Override
	public Set<User> findAllUsersForProduct(String productName) {
		Product product = this.products.get(productName);
		return product.getUsers();
	}

	@Override
	public Set<Theme> findAllThemesForProduct(String productName) {
		Product product = this.products.get(productName);
		return product.getThemes();
	}
	

}
