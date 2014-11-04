package com.cs4910.CBWebApp.domain;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;



public class Product implements Comparable<Product> {
	private Long id;
	private String name;
	private Set<Theme> themes = new TreeSet<Theme>();
	private Set<User> users = new TreeSet<User>();
	
	public Product() {
		super();
		this.id = System.currentTimeMillis();
		this.name = "Unknown Product";
	}
	
	public Product(String name) {
		super();
		this.id = System.currentTimeMillis();
		this.name = name;
	}
	
	public Product addTheme(Theme theme){
		this.themes.add(theme);
		theme.setProduct(this);		
		return this;
	}
	
	public Product addTheme(String name){
		Theme theme = new Theme(name);
		this.themes.add(theme);
		return this;
	}
	
	public Product addUser(User user){
		this.users.add(user);
		user.setProduct(this);
		return this;
	}
	
	public Product addUser(String name){
		User user = new User(name);
		this.users.add(user);
		return this;
	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Set<Theme> getThemes() {
		return Collections.unmodifiableSet(this.themes);
	}

	public Theme getTheme(String name) {
		Theme result = null;
		for (Theme t : this.themes) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return result;
	}	

	public void setThemes(Set<Theme> themes) {
		this.themes = themes;
	}


	public Set<User> getUsers() {
		return Collections.unmodifiableSet(this.users);
	}

	public User getUser(String name) {
		User result = null;
		for (User u : this.users) {
			if (u.getName().equals(name)) {
				return u;
			}
		}
		return result;
	}	
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}


	@Override
	public int compareTo(Product o) {
		return this.name.compareTo(o.name);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", n themes=" + themes.size()
				+ ", n users=" + users.size() + "]";
	}
	

}
