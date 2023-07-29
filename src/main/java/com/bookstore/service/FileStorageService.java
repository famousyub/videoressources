package com.bookstore.service;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.controller.VideoDetail;
import com.bookstore.entity.VideoFileDB;
import com.bookstore.repository.VideoFileRepository;


@Service
public class FileStorageService {

  @Autowired
  private VideoFileRepository fileDBRepository;

  public VideoFileDB store(VideoDetail video,MultipartFile file) throws IOException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    VideoFileDB FileDB = new VideoFileDB(fileName, file.getContentType(), file.getBytes());

    FileDB.setTitle(video.getTitle());
    FileDB.setDescription(video.getDescription());
    
    return fileDBRepository.save(FileDB);
  }

  public VideoFileDB getFile(String id) {
    return fileDBRepository.findById(id).get();
  }
  
  public Stream<VideoFileDB> getAllFiles() {
    return fileDBRepository.findAll().stream();
  }
}
