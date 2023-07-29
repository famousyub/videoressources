package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.MyVideo;


@Repository
public interface MyVideoRepository extends JpaRepository<MyVideo, Long> {

}
