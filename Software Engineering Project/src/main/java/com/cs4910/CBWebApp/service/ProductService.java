package com.cs4910.CBWebApp.service;

import java.util.Set;

import com.cs4910.CBWebApp.domain.DomainProduct;
import com.cs4910.CBWebApp.domain.DomainTheme;
import com.cs4910.CBWebApp.domain.DomainUser;

public interface ProductService {
	public Set<DomainProduct> findKanbanProducts();
	public Set<DomainProduct> findAllProducts();
	public Set<DomainUser> findAllUsersForProduct(String productName);
	public Set<DomainTheme> findAllThemesForProduct(String productName);

}
