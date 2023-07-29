package com.bookstore.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.entity.VideoGallery;
import com.bookstore.repository.VideoGalleryRepository;

@Service
public class VideoGalService {
	
	
	@Autowired
	private VideoGalleryRepository imageGalleryRepository;
	
	public void saveImage(VideoGallery imageGallery) {
		imageGalleryRepository.save(imageGallery);	
	}

	public List<VideoGallery> getAllActiveImages() {
		return imageGalleryRepository.findAll();
	}

	public Optional<VideoGallery> getImageById(Long id) {
		return imageGalleryRepository.findById(id);
	}

}
