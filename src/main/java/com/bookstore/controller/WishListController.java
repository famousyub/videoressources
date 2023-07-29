package com.bookstore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.bookstore.entity.MyVideo;
import com.bookstore.entity.Order;
import com.bookstore.entity.User;
import com.bookstore.entity.Wishlist;
import com.bookstore.service.UserService;
import com.bookstore.service.WishlistService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/wishlist")
@CrossOrigin("*")
public class WishListController {
	
	@Autowired
	private WishlistService wishListService;
	
	@Autowired
    private UserService userService;
	
	@RequestMapping("/getOrderList")
    public List<Order> getOrderList(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Order> orderList = user.getOrderList();

        return orderList;
    }
	
	
	
    @GetMapping("/userwishlist")
    public ResponseEntity<List<MyVideo>> getWishList(Principal principal) {
    	User user = userService.findByUsername(principal.getName());
          
            List<Wishlist> body = wishListService.readWishList(user.getId());
            List<MyVideo> products = new ArrayList<MyVideo>();
            for (Wishlist wishList : body) {
                    products.add(wishList.getProduct());
            }

            return new ResponseEntity<List<MyVideo>>(products, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addWishList(@RequestBody MyVideo product, Principal principal) {
    	User user = userService.findByUsername(principal.getName());
          
            Wishlist wishList = new Wishlist(user, product);
            wishListService.createWishlist(wishList);
            return  ResponseEntity.ok().body("created add " +  HttpStatus.CREATED);

    }

}
