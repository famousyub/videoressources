package com.bookstore.controller;

public class MyVidoResponse {
	
	  private byte[] image;
	  
	  private String type;
	  private String name;
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public MyVidoResponse() {
		super();
	}
	public MyVidoResponse(String name, String type, byte[] picByte) {
		this.name = name;
		this.type = type;
		this.image = picByte;
	}
	  
	  

}
