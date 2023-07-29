package com.bookstore.entity;



import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "myvideo")
public class MyVideo implements Serializable {
	
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "description", nullable = false)
	private String description;	
	
	@Column(name = "videonumber",nullable = false, precision = 10, scale = 2)
    private int videonumber;
	
	@Lob
    @Column(name = "Image", nullable = true)
    private byte[] image;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    private Date createDate;
    
    @Column(name = "title", nullable = true)
    private String title;
    @Column(name = "type", nullable = true)
	private String type;
    
    @Column(name="uri",nullable = true)
	private String uri;
    
    
    
    
	public MyVideo(Long id, String name, String description, int videonumber, byte[] image, Date createDate,
			String title, String type, String uri, List<Wishlist> wishListList) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.videonumber = videonumber;
		this.image = image;
		this.createDate = createDate;
		this.title = title;
		this.type = type;
		this.uri = uri;
		this.wishListList = wishListList;
	}
	@JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Wishlist> wishListList;
    
    
	public List<Wishlist> getWishListList() {
		return wishListList;
	}
	public void setWishListList(List<Wishlist> wishListList) {
		this.wishListList = wishListList;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getVideonumber() {
		return videonumber;
	}
	public void setVideonumber(int videonumber) {
		this.videonumber = videonumber;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public MyVideo(Long id, String name, String description, int videonumber, byte[] image, Date createDate,
			String title, String type) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.videonumber = videonumber;
		this.image = image;
		this.createDate = createDate;
		this.title = title;
		this.type = type;
	}
	
	public MyVideo(Long id, String name, String description, int videonumber, byte[] image, Date createDate,
			String title, String type, String uri) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.videonumber = videonumber;
		this.image = image;
		this.createDate = createDate;
		this.title = title;
		this.type = type;
		this.uri = uri;
	}
	public MyVideo() {
		super();
	}
    
    
	  

}
