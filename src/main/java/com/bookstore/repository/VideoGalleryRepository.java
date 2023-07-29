package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.VideoGallery;


@Repository
public interface VideoGalleryRepository extends JpaRepository<VideoGallery, Long> {

}
