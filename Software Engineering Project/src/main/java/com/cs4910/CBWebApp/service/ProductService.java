package com.cs4910.CBWebApp.service;

import java.util.Set;

import com.cs4910.CBWebApp.domain.DomainProduct;
import com.cs4910.CBWebApp.domain.Theme;
import com.cs4910.CBWebApp.domain.User;

public interface ProductService {
	public Set<DomainProduct> findKanbanProducts();
	public Set<DomainProduct> findAllProducts();
	public Set<User> findAllUsersForProduct(String productName);
	public Set<Theme> findAllThemesForProduct(String productName);

}
