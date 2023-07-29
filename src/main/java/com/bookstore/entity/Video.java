package com.bookstore.entity;


import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Video implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String url; // This will store the URL or file path of the video
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public Video() {
		super();
	}
	public Video(int id, String title, String description, String url) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.url = url;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
    
    
    // Constructors, getters, and setters (omitted for brevity)
}
