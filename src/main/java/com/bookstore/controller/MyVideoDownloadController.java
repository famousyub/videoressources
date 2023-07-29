package com.bookstore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.entity.MyVideo;
import com.bookstore.repository.MyVideoRepository;

@RestController
@RequestMapping("api/v1/myvideo")
@CrossOrigin("*")
public class MyVideoDownloadController {
	
	@Autowired
	private MyVideoRepository myvideorepo ;
	
	
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllVideo()
	{
		
		List<MyVideo> video = myvideorepo.findAll();
		
		
		List<String>  uri = new ArrayList<>();
		
		
		for (MyVideo my: video)
		{
			uri.add(my.getUri());
		}
		
		return ResponseEntity.ok().body(uri);
	}

}
