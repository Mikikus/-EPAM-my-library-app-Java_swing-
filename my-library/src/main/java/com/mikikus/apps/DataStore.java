package com.mikikus.apps;
import java.io.Serializable;
import java.util.ArrayList;

public class DataStore implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Books> books;
	private int count; //amount of books in library
	
	public DataStore() {
		books = new ArrayList<>();
		count = -1;
	}
	
	public DataStore(DataStore temp) {
		this.count = temp.count;
		this.books = temp.books;
	}
	
	public ArrayList<Books> getBooks(){
		return books;
	}

	public int getCount() {
		return count;
	}
	
	public void countPlusOne() {
		this.count = this.count + 1;
	}
	
	public void countMinusOne() {
		this.count = this.count - 1;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
