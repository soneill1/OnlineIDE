package edu.neu.cs5200.s3.onlineide.applications;

public class Hello {
	
	public String sayHello(String name) {
		return "Hello "+name;
	}

	public static void main(String[] args) {
		System.out.println("Hello World");
		
		String a = "10";
		String b = a;
		
		b = "11"; 
		System.out.println(a);

	}

}
