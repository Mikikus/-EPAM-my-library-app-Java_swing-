package com.mikikus.apps;

import java.io.Serializable;

public class Books implements Serializable {
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name, author, publisher, binding, price, year, pages;
	
	
	public Books(int id, String name, String Auth, String publ, String y, String pg, String prc, String bindie) {
		this.ID = id;
		this.name = name;
		this.author = Auth;
		this.publisher = publ;
		this.year = y;
		this.pages = pg;
		this.price = prc;
		this.binding = bindie;
	}
	
	public Books() {
		this.ID = 0;
		this.name = " ";
		this.author = " ";
		this.publisher = " ";
		this.year = " ";
		this.pages = " ";
		this.price = " ";
		this.binding = " ";
	}
	
	//@Override
	//public String toString() {
	//	return String.format("ID: %d\nName: %s\nAuthor: %s\nPublisher: %s\nPublish Year: %d\nPages: %d\nPrice: %.2f$\nBinding: %s", 
	//			ID, name, author, publisher, year, pages, price, binding);
	//}
	
	@Override
	public String toString() {
		return " ID: " + ID + "\n Title: " + name + "\n Author: " + author + "\n Publisher: " + publisher + "\n Binding: "
				+ binding + "\n Price: " + price + "\n Year: " + year + "\n Pages: " + pages;
	}

	public String getName() {
		return name;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public String getBinding() {
		return binding;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getPrice() {
		return price;
	}
	
	public String getPages() {
		return pages;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setName(String NAME) {
		this.name = NAME;
	}
	
	public void setAuthor(String Auth) {
		this.author = Auth;
	}
	
	public void setPublisher(String Publ) {
		this.publisher = Publ;
	}
	
	public void setID(int id) {
		this.ID = id;
	}
	
	public void setPages(String pg) {
		this.pages = pg;
	}
	
	public void setBinding(String bindie) {
		this.binding = bindie;
	}
	
	public void setYear(String yr) {
		this.year = yr;
	}
	
	public void setPrice(String prc) {
		this.price = prc;
	}
	
}
