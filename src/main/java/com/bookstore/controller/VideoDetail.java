package com.bookstore.controller;

public class VideoDetail {
	
	private String title;
	private String description;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public VideoDetail(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}
	public VideoDetail() {
		super();
	}

}
