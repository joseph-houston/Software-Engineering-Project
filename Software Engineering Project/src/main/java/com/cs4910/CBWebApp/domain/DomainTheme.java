package com.cs4910.CBWebApp.domain;

public class DomainTheme implements Comparable<DomainTheme> {
	private long id;
	private String name;
	private DomainProduct product;
	
	public DomainTheme() {
		this.name = "unknown";
		this.product = new DomainProduct("unknown");
		this.id = System.currentTimeMillis();
	}

	public DomainTheme(String name, Long id) {
		this.name = name;
		this.product = new DomainProduct("unknown");
		this.id = id;
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
	public int compareTo(DomainTheme o) {
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
		DomainTheme other = (DomainTheme) obj;
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
