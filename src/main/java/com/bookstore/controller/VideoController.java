package com.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookstore.entity.Video;
import com.bookstore.repository.VideoRepository;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@CrossOrigin("*")
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    @GetMapping
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable int id) {
        return videoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Video createVideo(@RequestBody Video video) {
        return videoRepository.save(video);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Video> updateVideo(@PathVariable int id, @RequestBody Video updatedVideo) {
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
