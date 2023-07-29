package com.bookstore.entity;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.*;
@Entity
public class VideoEvent implements Serializable {
	
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String url;
    
    private boolean event;

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

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isEvent() {
		return event;
	}

	public VideoEvent() {
		super();
	}

	public VideoEvent(int id, String title, String description, String url, boolean event) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.url = url;
		this.event = event;
	}

	public void setEvent(boolean event) {
		this.event = event;
	}
    

}
