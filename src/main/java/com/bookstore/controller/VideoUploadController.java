package com.bookstore.controller;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bookstore.entity.Video;
import com.bookstore.payload.Response;
import com.bookstore.repository.VideoRepository;
//import com.bookstore.service.FileStorageService;
import com.bookstore.service.VideoFileService;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class VideoUploadController {

	@Autowired
    private VideoFileService fileStorageService;
	
	@Autowired
	private VideoRepository videoRepository;
	
	
	

    @PostMapping("/uploadFile")
    public Response uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

         Video video = new Video();
         video.setTitle(fileName);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/downloadFile/")
            .path(fileName)
            .toUriString();
        video.setDescription(file.getContentType() +" " + file.getName());
        
        video.setUrl(fileDownloadUri);

        return new Response(fileName, fileDownloadUri,
            file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List < Response > uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
            .stream()
            .map(file -> uploadFile(file))
            .collect(Collectors.toList());
    }
}
