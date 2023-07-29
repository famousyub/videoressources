package com.bookstore.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "files")

public class VideoFileDB {
	
	
	@Id
	  @GeneratedValue(generator = "uuid")
	  @GenericGenerator(name = "uuid", strategy = "uuid2")
	  private String id;

	
	  private String title ;
	  private String description;
	  private String name;

	  private String type;
	

	  public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public VideoFileDB(String id, String title, String description, String name, String type, byte[] data) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.name = name;
		this.type = type;
		this.data = data;
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

	@Lob
	  private byte[] data;

	  public VideoFileDB() {
	  }

	  public VideoFileDB(String name, String type, byte[] data) {
	    this.name = name;
	    this.type = type;
	    this.data = data;
	  }

	  public String getId() {
	    return id;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public String getType() {
	    return type;
	  }

	  public void setType(String type) {
	    this.type = type;
	  }

	  public byte[] getData() {
	    return data;
	  }

	  public void setData(byte[] data) {
	    this.data = data;
	  }

}
