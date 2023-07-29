package com.bookstore.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.entity.Video;
import com.bookstore.entity.VideoEvent;
import com.bookstore.payload.Response;
import com.bookstore.repository.VideoEventRepository;
import com.bookstore.repository.VideoRepository;
import com.bookstore.upload.FileUploadResponse;
import com.bookstore.upload.FileUploadUtil;
 
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v0")
public class FileUploadController {
     
	@Autowired
	private VideoEventRepository videoRepository;
	
    @PostMapping("/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile multipartFile)
                    throws IOException {
         
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
        VideoEvent video = new VideoEvent();
        String filecode = FileUploadUtil.saveFile(fileName, multipartFile);
         
        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/" + filecode);
         
        video.setDescription(filecode);
        video.setTitle(fileName);
        video.setUrl("/downloadFile/" + filecode);
        videoRepository.save(video);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/uploadMultipleFiles")
    public List<ResponseEntity<FileUploadResponse>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
            .stream()
            .map(file -> {
				try {
					return uploadFile(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			})
            .collect(Collectors.toList());
    }
}