package com.bookstore.message;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bookstore.controller.VideoDetail;
import com.bookstore.entity.VideoFileDB;
import com.bookstore.service.FileStorageService;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/v3")
public class FileController {

  @Autowired
  private FileStorageService storageService;
  
  MultipartFile f ;
  
  
  
  
  
  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
	  String message = "";
	  try {
		  VideoDetail  v =new VideoDetail();
		  v.setTitle(file.getOriginalFilename());
		  v.setDescription(file.getContentType());
		  storageService.store(v, file);
		  System.out.println("oookkkkkkkkkkkkkk");
		   
	     this.f= file;

	      message = "Uploaded the file successfully: " + file.getOriginalFilename();
	      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	    } catch (Exception e) {
	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	    }
  }
  
  

  @PostMapping("/addvideo")
  public ResponseEntity<ResponseMessage> sendfiledata(@RequestBody VideoDetail  v) throws IOException {
    String message = "";
    System.out.println("oookkkkkkkkkkkkkk" );
    System.out.println(this.f);
    
    storageService.store(v, this.f);
    return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//    System.out.println(this.f.getName());
//    try {
//		   
//    	storageService.store(v, this.f);
//
//	      message = "insert succesfully " + this.f.getOriginalFilename();
//	      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//	    } catch (Exception e) {
//	      message = "insert error";
//	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//	    }
  }

  @GetMapping("/files")
  public ResponseEntity<List<ResponseFile>> getListFiles() {
    List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/api/v3/files/")
          .path(dbFile.getId())
          .toUriString();

      return new ResponseFile(
          dbFile.getName(),
          fileDownloadUri,
          dbFile.getType(),
          dbFile.getData().length);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }

  @GetMapping("/files/{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable String id) {
    VideoFileDB fileDB = storageService.getFile(id);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
        .body(fileDB.getData());
  }
}