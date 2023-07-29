package com.bookstore.service;



import java.util.List;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.bookstore.entity.Wishlist;
import com.bookstore.repository.WoshlistRepository;


@Service
@Transactional
public class WishlistService {
	
	  private final WoshlistRepository wishListRepository;

	    public  WishlistService(WoshlistRepository wishListRepository) {
	        this.wishListRepository = wishListRepository;
	    }

	    public void createWishlist(Wishlist wishList) {
	        wishListRepository.save(wishList);
	    }

	    public List<Wishlist> readWishList(Integer userId) {
	        return wishListRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
	    }

}
