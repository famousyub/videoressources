package com.bookstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.entity.MyVideo;
import com.bookstore.repository.MyVideoRepository;
import com.bookstore.service.MyVideoService;


@Service
public class MyVideoServiceImpl implements MyVideoService {

	
	@Autowired
	private MyVideoRepository myVideoRepository;
	
	@Override
	public List<String> getAllVideoNames() {
		// TODO Auto-generated method stub
		return myVideoRepository.findAll() 
				.stream().map(r->{
					
					return r.getUri();
				}).toList();
	}

	@Override
	public MyVideo getVideo(Long name) {
		// TODO Auto-generated method stub
		 return myVideoRepository.findById(name).get();
	}

}
