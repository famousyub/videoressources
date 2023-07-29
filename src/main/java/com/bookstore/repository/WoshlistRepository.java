package com.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.Wishlist;

@Repository
public interface WoshlistRepository extends JpaRepository<Wishlist, Integer>{
	 List<Wishlist> findAllByUserIdOrderByCreatedDateDesc(Integer userId);

}
