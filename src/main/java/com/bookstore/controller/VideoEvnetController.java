package com.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookstore.entity.VideoEvent;
import com.bookstore.repository.VideoEventRepository;

import java.util.List;

@RestController
@RequestMapping("/api/videosevent")
@CrossOrigin("*")
public class VideoEvnetController {

	
	@Autowired
    private VideoEventRepository videoRepository;

    @GetMapping
    public List<VideoEvent> getAllVideos() {
        return videoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoEvent> getVideoById(@PathVariable int id) {
        return videoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public VideoEvent createVideo(@RequestBody VideoEvent video) {
        return videoRepository.save(video);
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<VideoEvent> updateVideo(@PathVariable int id, @RequestBody VideoEvent updatedVideo) {
        return videoRepository.findById(id)
                .map(video -> {
                    video.setTitle(updatedVideo.getTitle());
                    video.setDescription(updatedVideo.getDescription());
                    video.setUrl(updatedVideo.getUrl());
                    videoRepository.save(video);
                    return ResponseEntity.ok(video);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVideo(@PathVariable int id) {
        return videoRepository.findById(id)
                .map(video -> {
                    videoRepository.delete(video);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
	
	
}
