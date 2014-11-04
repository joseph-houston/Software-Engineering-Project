package com.cs4910.CBWebApp.service;

import java.util.Set;

import com.cs4910.CBWebApp.domain.Product;
import com.cs4910.CBWebApp.domain.Theme;
import com.cs4910.CBWebApp.domain.User;

public interface ProductService {
	public Set<Product> findAllProducts();
	public Set<User> findAllUsersForProduct(String productName);
	public Set<Theme> findAllThemesForProduct(String productName);

}
