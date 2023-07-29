package com.bookstore.entity;



import javax.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


@Entity
@Table(name = "wishlist")
public class Wishlist implements Serializable {
	
	
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

       
	    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	    @JoinColumn(nullable = false)
	    private User user;

	    @Column(name = "created_date")
	    private Date createdDate;
	    
	    
	    @ManyToOne()
	    @JoinColumn(name = "video_id")
	    private MyVideo product;


		public Integer getId() {
			return id;
		}


		public void setId(Integer id) {
			this.id = id;
		}


		public User getUser() {
			return user;
		}


		public void setUser(User user) {
			this.user = user;
		}


		public Date getCreatedDate() {
			return createdDate;
		}


		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}


		public MyVideo getProduct() {
			return product;
		}


		public void setProduct(MyVideo product) {
			this.product = product;
		}
		


		public Wishlist(User user, MyVideo product) {
			super();
			this.user = user;
			this.product = product;
		}


		public Wishlist(Integer id, User user, Date createdDate, MyVideo product) {
			super();
			this.id = id;
			this.user = user;
			this.createdDate = createdDate;
			this.product = product;
		}


		public Wishlist() {
			super();
		}
	    
	    
 


}
