package com.cs4910.CBWebApp.domain;

public class User implements Comparable<User> {
	private long id;
	private String name;
	private DomainProduct product;
	
	public User() {
		this.name = "unknown";
		this.product = new DomainProduct("unknown");
		this.id = System.currentTimeMillis();
	}

	public User(String name) {
		this.name = name;
		this.product = new DomainProduct("unknown");
		this.id = System.currentTimeMillis();
	}	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DomainProduct getProduct() {
		return product;
	}
	public void setProduct(DomainProduct product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", product=" + product
				+ "]";
	}

	@Override
	public int compareTo(User o) {
		return this.name.compareTo(o.name);
	}

}
