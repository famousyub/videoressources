package com.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bookstore.entity.MyVideo;
import com.bookstore.service.MyVideoService;

@Controller
public class VideoPlayerController {
	
	@Autowired
	private MyVideoService imageGalleryService;
	@GetMapping(value = {"/videocontr", "/homeplayer"})
	public String addProductPage(Model model) {
		
		MyVideo imageGallery = imageGalleryService.getVideo(Long.parseLong("1"));
		
    	
        byte[] data = imageGallery.getImage();
		model.addAttribute("vid","http://localhost:8080/api/v3/imagevid/1");
		return "onevid";
	}

}
