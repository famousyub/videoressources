package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.VideoFileDB;

@Repository
public interface VideoFileRepository extends JpaRepository<VideoFileDB, String> {

	
	@Query("select u from VideoFileDB u where u.title = ?1")
	  VideoFileDB findFileBytitle(@Param("title") String title);
}
