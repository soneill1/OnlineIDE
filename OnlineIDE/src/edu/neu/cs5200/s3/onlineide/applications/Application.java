package edu.neu.cs5200.s3.onlineide.applications;

// only used for holding values, or POJO
public class Application {
	
	private int id = -1;
	private String name = "";
	private double price;
	
	public Application() {
		super();
	}
	
	public Application(String name, double price) {
		super();
		this.name = name;
		this.price = price;
	}
	
	public Application(int id, String name, double price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
