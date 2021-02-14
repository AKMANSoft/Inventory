package com.mi6.model;

public class Product implements Comparable<Product>{

	private int id;
	private String name;
	private int quanity;
	private int current;
	private int orderId;
	
	public Product(int id, String name, int quanity, int orderId) {
		super();
		this.id = id;
		this.name = name;
		this.quanity = quanity;
		this.orderId  = orderId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuanity() {
		return quanity;
	}

	public void setQuanity(int quanity) {
		this.quanity = quanity;
	}
	
	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", quanity=" + quanity + "]";
	}

	@Override
	public int compareTo(Product o) {
		return ((Integer)orderId).compareTo((Integer)o.getOrderId());
	}
	
}
