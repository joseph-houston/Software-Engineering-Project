package com.cs4910.CBWebApp.domain;

public class Theme implements Comparable<Theme> {
	private long id;
	private String name;
	private Product product;
	
	public Theme() {
		this.name = "unknown";
		this.product = new Product("unknown");
		this.id = System.currentTimeMillis();
	}

	public Theme(String name) {
		this.name = name;
		this.product = new Product("unknown");
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



	public Product getProduct() {
		return product;
	}



	public void setProduct(Product product) {
		this.product = product;
	}



	@Override
	public int compareTo(Theme o) {
		return this.name.compareTo(o.name);
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
		Theme other = (Theme) obj;
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
		return "Theme [id=" + id + ", name=" + name + ", product=" + product
				+ "]";
	}

}