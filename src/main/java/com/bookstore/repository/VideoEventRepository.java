package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.VideoEvent;



@Repository
public interface VideoEventRepository extends JpaRepository<VideoEvent, Integer>  {

}
