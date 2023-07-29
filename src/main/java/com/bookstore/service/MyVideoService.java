package com.bookstore.service;

import java.util.List;

import com.bookstore.entity.MyVideo;

public interface  MyVideoService {
	
	

    List<String> getAllVideoNames();
    
    MyVideo getVideo(Long name);

}
