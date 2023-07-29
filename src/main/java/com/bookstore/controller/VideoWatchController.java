package com.bookstore.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.entity.MyVideo;
import com.bookstore.repository.MyVideoRepository;
import com.bookstore.service.MyVideoService;
import com.bookstore.service.VideoStreamService;
import com.bookstore.utility.AppVideo;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v3")
@CrossOrigin("*")
public class VideoWatchController {
	
	
	@Value("${uploadDir}")
	private String uploadFolder;

	private Path foundFile;
	@Autowired
	private MyVideoService imageGalleryService;
	
	  private final VideoStreamService videoStreamService;

	    public VideoWatchController(VideoStreamService videoStreamService) {
	        this.videoStreamService = videoStreamService;
	    }
	    
	    
	    @GetMapping(value = "/imagevid/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE,consumes =MediaType.APPLICATION_OCTET_STREAM_VALUE )
	    
	    public ResponseEntity<byte[]> video(@RequestHeader(value = "Range",required = false) String range,@PathVariable("id") Long id) throws IOException {
	        
	    	
	    	MyVideo imageGallery = imageGalleryService.getVideo(id);
	    	
	        byte[] data = imageGallery.getImage();

	        return  ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
	                .header("Content-Type", "video/mp4")
	                
	                
	             
	                .body( AppVideo.decompressBytes(data));
	    }
	    
	    /*public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
	    	MyVideo imageGallery = imageGalleryService.getVideo(id);
	        // Replace the following with your logic to fetch the image data as bytes
	        byte[] imageData = imageGallery.getImage();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headers.setContentDisposition(ContentDisposition.parse(  "attachment; filename=\"" + imageGallery.getName() + "\""));
	        headers.setContentLength(imageData.length);
	        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
	        
	    }*/
	    
	    
	    @GetMapping("/videoslm/{videoName}")
	    public ResponseEntity<InputStreamResource> getVideoload(@PathVariable String videoName,HttpServletRequest request) throws IOException {
	        // Read the video file from the directory where you saved it or retrieve it from the database
	        //String videoFilePath = "/path/to/videos/directory/" + videoName;
	    	String uploadDirectory = request.getServletContext().getRealPath("Files-Upload");
	        String filePath = Paths.get(uploadDirectory, videoName).toString();
	        InputStream videoFileStream = new FileInputStream(filePath);
	        InputStreamResource videoInputStreamResource = new InputStreamResource(videoFileStream);

	        return ResponseEntity.ok()
	                .header("Content-Disposition", "inline; filename=" + videoName)
	                .contentLength(videoFileStream.available())
	                .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
	                .body(videoInputStreamResource);
	    }
	    
	    @GetMapping("/video/display/{id}")
		@ResponseBody
		ResponseEntity<?> showImage(@PathVariable("id") Long id, HttpServletResponse response)
				throws ServletException, IOException {
		
			MyVideo imageGallery = imageGalleryService.getVideo(id);
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif ,video/* ");
			response.getOutputStream().write(imageGallery.getImage());
			response.getOutputStream().close();
			
			return ResponseEntity.ok()
	                .header("Content-Disposition", "inline; filename=" + imageGallery.getName())
	             
	                .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
	                .body(response);
		}
	    
	    @GetMapping("/videos/{videoName}")
	    public ResponseEntity<InputStreamResource> getVideo(@PathVariable String videoName) throws IOException {
	    	
	    	
	        // Read the video file from the directory where you saved it or retrieve it from the database
	        String videoFilePath = "/path/to/videos/directory/" + videoName;

	        InputStream videoFileStream = new FileInputStream(videoFilePath);
	        InputStreamResource videoInputStreamResource = new InputStreamResource(videoFileStream);

	        return ResponseEntity.ok()
	                .header("Content-Disposition", "inline; filename=" + videoName)
	                .contentLength(videoFileStream.available())
	                .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
	                .body(videoInputStreamResource);
	    }
	@Autowired
	private MyVideoRepository myVideoRepository;
	
	@GetMapping("/all/video/title")
	@ResponseBody
	public ResponseEntity<?> alltitleVideo()
	{
		
		List<String> titles = myVideoRepository.findAll()
				
				.stream()
				.map(r-> r.getTitle())
				.toList();
		
		return ResponseEntity.ok().body(titles);
	}
	
	
	
	
}
