package com.cs4910.CBWebApp.domain;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;



public class DomainProduct implements Comparable<DomainProduct> {
	private Long id;
	private String name;
	private Set<DomainTheme> themes = new TreeSet<DomainTheme>();
	private Set<DomainUser> users = new TreeSet<DomainUser>();
	
	public DomainProduct() {
		super();
		this.id = System.currentTimeMillis();
		this.name = "Unknown Product";
	}
	
	public DomainProduct(String name) {
		super();
		this.id = System.currentTimeMillis();
		this.name = name;
	}
	
	public DomainProduct addTheme(DomainTheme theme){
		this.themes.add(theme);
		theme.setProduct(this);		
		return this;
	}
	
	public DomainProduct addTheme(String name){
		DomainTheme theme = new DomainTheme(name);
		this.themes.add(theme);
		return this;
	}
	
	public DomainProduct addUser(DomainUser user){
		this.users.add(user);
		user.setProduct(this);
		return this;
	}
	
	public DomainProduct addUser(String name){
		DomainUser user = new DomainUser(name);
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


	public Set<DomainTheme> getThemes() {
		return Collections.unmodifiableSet(this.themes);
	}

	public DomainTheme getTheme(String name) {
		DomainTheme result = null;
		for (DomainTheme t : this.themes) {
			if (t.getName().equals(name)) {
				return t;
			}
		}
		return result;
	}	

	public void setThemes(Set<DomainTheme> themes) {
		this.themes = themes;
	}


	public Set<DomainUser> getUsers() {
		return Collections.unmodifiableSet(this.users);
	}

	public DomainUser getUser(String name) {
		DomainUser result = null;
		for (DomainUser u : this.users) {
			if (u.getName().equals(name)) {
				return u;
			}
		}
		return result;
	}	
	
	public void setUsers(Set<DomainUser> users) {
		this.users = users;
	}


	@Override
	public int compareTo(DomainProduct o) {
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
		DomainProduct other = (DomainProduct) obj;
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
