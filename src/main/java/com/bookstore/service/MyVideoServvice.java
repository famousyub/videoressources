package com.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.entity.MyVideo;

import com.bookstore.repository.MyVideoRepository;


@Service
public class MyVideoServvice {
	
	@Autowired
	private MyVideoRepository imageGalleryRepository;
	
	public void saveImage(MyVideo imageGallery) {
		imageGalleryRepository.save(imageGallery);	
	}

	public List<MyVideo> getAllActiveImages() {
		return imageGalleryRepository.findAll();
	}

	public Optional<MyVideo> getImageById(Long id) {
		return imageGalleryRepository.findById(id);
	}
	
	

}
