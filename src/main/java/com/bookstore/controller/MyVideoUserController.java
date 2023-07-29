package com.bookstore.controller;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.bookstore.entity.MyVideo;

import com.bookstore.exception.FileNotFoundException;
import com.bookstore.repository.MyVideoRepository;
import com.bookstore.service.MyVideoService;
import com.bookstore.service.VideoStreamService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class MyVideoUserController {
	
	@Autowired
	private MyVideoRepository myVideoRepository;
	
	
	@Autowired
	private MyVideoService myVideoService;
	
	@Autowired
	private VideoStreamService videoStreamService;
	
	
	private final String VIDEO_PATH= "attachment; filename=\";";
	
			
	
	
	public byte[] readByteRange(String filename, long start, long end) throws IOException {

	    FileInputStream inputStream = new FileInputStream(VIDEO_PATH + filename);
	    ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream();
	    byte[] data = new byte[1024];
	    int nRead;
	    while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
	        bufferedOutputStream.write(data, 0, nRead);
	    }
	    bufferedOutputStream.flush();
	    byte[] result = new byte[(int) (end - start)];
	    System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, (int) (end - start));
	    return result;
	}
	
	
	
	@GetMapping("/stream/{fileType}/{fileName}")
    public ResponseEntity<ResponseEntity<byte[]>> streamVideo(ServerHttpResponse serverHttpResponse, @RequestHeader(value = "Range", required = false) String httpRangeList,
                                                    @PathVariable("fileType") String fileType,
                                                    @PathVariable("fileName") String fileName) {
		
		String vidtype= "video/*";
        return    ResponseEntity
        		
	               .ok(videoStreamService.prepareContent(fileName, vidtype, httpRangeList));
    }
	
	 @GetMapping("/myvideo/{name}")
	   public ResponseEntity<Resource> getVideoByName(@PathVariable("name") Long name){
	       return ResponseEntity
	               .ok(new ByteArrayResource(myVideoService.getVideo(name).getImage()));
	   }
	 
	 
	   @GetMapping("/myvideoid/{id}")
	   @ResponseBody
	   public ResponseEntity<Resource> getVideoById(@PathVariable("id") Long name){
	      
		   final HttpHeaders responseHeaders = new  HttpHeaders();
			  responseHeaders.add("Content-Type", "video/mp4");
		   return ResponseEntity
	               .status(HttpStatus.OK)
	               .contentType(MediaType.APPLICATION_OCTET_STREAM)
	               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + myVideoService.getVideo(name).getName() + "\"")
	               .body(new ByteArrayResource(myVideoService.getVideo(name).getImage()));
	   }

	   @GetMapping("/myvideostream/all")
	   public ResponseEntity<List<String>> getAllVideoNames(){
	       return ResponseEntity
	               .ok(myVideoService.getAllVideoNames());
	   }
	
	
	 @GetMapping("/myvideo/files/{id}")
	 @ResponseBody
	  public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
		  MyVideo video = myVideoRepository.findById(id).get();
		   final HttpHeaders responseHeaders = new  HttpHeaders();
		  responseHeaders.add("Content-Type", "video/mp4");
	       //  responseHeaders.add("Content-Length", video.getImage().toString());
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + video.getName() + "\"")
	        .body(video.getImage());
	    
	    
	    
	  
	  }
	
	
	
	@GetMapping(value = "/play/media/v01/{vid_id}")
	@ResponseBody
	public ResponseEntity<StreamingResponseBody> playMediaV01(
	   @PathVariable("vid_id")
	   Long  video_id,
	   @RequestHeader(value = "Range", required = false)
	   String rangeHeader)
	{
		
		MyVideo video = myVideoRepository.findById(video_id).get();
	   try
	   {
	      StreamingResponseBody responseStream;
	      String filePathString = "<Place your MP4 file full path here.>";
	      Path filePath = Paths.get(filePathString);
	      Long fileSize = Files.size(filePath);
	      byte[] buffer = new byte[1024];      
	      final HttpHeaders responseHeaders = new  HttpHeaders();

	      if (rangeHeader == null)
	      {
	         responseHeaders.add("Content-Type", "video/mp4");
	         responseHeaders.add("Content-Length", fileSize.toString());
	         responseStream = os -> {
	            RandomAccessFile file = new RandomAccessFile(filePathString, "r");
	            try (file)
	            {
	               long pos = 0;
	               file.seek(pos);
	               while (pos < fileSize - 1)
	               {                            
	                  file.read(buffer);
	                  os.write(buffer);
	                  pos += buffer.length;
	               }
	               os.flush();
	            } catch (Exception e) {}
	         };
	         
	         return new ResponseEntity<StreamingResponseBody>
	                (responseStream, responseHeaders, HttpStatus.OK);
	      }

	      String[] ranges = rangeHeader.split("-");
	      Long rangeStart = Long.parseLong(ranges[0].substring(6));
	      Long rangeEnd;
	      if (ranges.length > 1)
	      {
	         rangeEnd = Long.parseLong(ranges[1]);
	      }
	      else
	      {
	         rangeEnd = fileSize - 1;
	      }
	         
	      if (fileSize < rangeEnd)
	      {
	         rangeEnd = fileSize - 1;
	      }

	      String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
	      responseHeaders.add("Content-Type", "video/mp4");
	      responseHeaders.add("Content-Length", contentLength);
	      responseHeaders.add("Accept-Ranges", "bytes");
	      responseHeaders.add("Content-Range", "bytes" + " " + 
	                           rangeStart + "-" + rangeEnd + "/" + fileSize);
	      final Long _rangeEnd = rangeEnd;
	      responseStream = os -> {
	         RandomAccessFile file = new RandomAccessFile(filePathString, "r");
	         try (file)
	         {
	            long pos = rangeStart;
	            file.seek(pos);
	            while (pos < _rangeEnd)
	            {                        
	               file.read(buffer);
	               os.write(buffer);
	               pos += buffer.length;
	            }
	            os.flush();
	         }
	         catch (Exception e) {}
	      };
	         
	      return new ResponseEntity<StreamingResponseBody>
	             (responseStream, responseHeaders, HttpStatus.PARTIAL_CONTENT);
	   }
	   catch (FileNotFoundException e)
	   {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	   }
	   catch (IOException e)
	   {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	}

}
