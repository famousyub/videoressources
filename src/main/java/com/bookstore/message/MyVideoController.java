package com.bookstore.message;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.entity.MyVideo;

import com.bookstore.service.MyVideoServvice;

import com.bookstore.upload.FileUploadResponse;
import com.bookstore.upload.FileUploadUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
@Controller
public class MyVideoController {
	
	
	
	@Value("${uploadDir}")
	private String uploadFolder;

	@Autowired
	private MyVideoServvice imageGalleryService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@GetMapping(value = {"/myvideo", "/homevideo"})
	public String addProductPage() {
		return "myvideo";
	}

	@PostMapping("/video/saveImageDetails")
	public @ResponseBody ResponseEntity<?> createProduct(@RequestParam("name") String name,
			@RequestParam("numbervideo") int numbervideo, @RequestParam("description") String description, Model model, HttpServletRequest request
			,final @RequestParam("image") MultipartFile file) {
		try {
			//String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
			String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
			log.info("uploadDirectory:: " + uploadDirectory);
			
			
			String fileName_ = StringUtils.cleanPath(file.getOriginalFilename());
	        long size = file.getSize();
	     
	        String filecode = FileUploadUtil.saveFile(fileName_, file);
	         
	        FileUploadResponse response = new FileUploadResponse();
	        response.setFileName(fileName_);
	        response.setSize(size);
	        response.setDownloadUri("/downloadFile/" + filecode);
	         
	       
			
			String fileName = file.getOriginalFilename();
			String filePath = Paths.get(uploadDirectory, fileName).toString();
			log.info("FileName: " + file.getOriginalFilename());
			if (fileName == null || fileName.contains("..")) {
				model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
				return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
			}
			String[] names = name.split(",");
			String[] descriptions = description.split(",");
			Date createDate = new Date();
			log.info("Name: " + names[0]+" "+filePath);
			log.info("description: " + descriptions[0]);
			log.info("numbervideo: " + numbervideo);
			try {
				File dir = new File(uploadDirectory);
				if (!dir.exists()) {
					log.info("Folder Created");
					dir.mkdirs();
				}
				// Save the file locally
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(file.getBytes());
				stream.close();
			} catch (Exception e) {
				log.info("in catch");
				e.printStackTrace();
			}
			byte[] imageData = file.getBytes();
			MyVideo imageGallery = new MyVideo();
			compressBytes(file.getBytes());
			imageGallery.setName(names[0]);
			imageGallery.setImage(compressBytes(file.getBytes()));
			imageGallery.setVideonumber(numbervideo);
			imageGallery.setDescription(descriptions[0]);
			imageGallery.setCreateDate(createDate);
			
			
			imageGallery.setTitle(fileName);
			imageGallery.setUri("/downloadFile/" + filecode);
			imageGallery.setType(file.getContentType());
			
			imageGalleryService.saveImage(imageGallery);
			log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
			return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Exception: " + e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/video/display/{id}")
	@ResponseBody
	void showImage(@PathVariable("id") Long id, HttpServletResponse response, Optional<MyVideo> imageGallery)
			throws ServletException, IOException {
		log.info("Id :: " + id);
		imageGallery = imageGalleryService.getImageById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif ,video/* ");
		response.getOutputStream().write(imageGallery.get().getImage());
		response.getOutputStream().close();
	}

	@GetMapping("/video/imageDetails")
	String showProductDetails(@RequestParam("id") Long id, Optional<MyVideo> imageGallery, Model model) {
		try {
			log.info("Id :: " + id);
			if (id != 0) {
				imageGallery = imageGalleryService.getImageById(id);
			
				log.info("products :: " + imageGallery);
				if (imageGallery.isPresent()) {
					model.addAttribute("id", imageGallery.get().getId());
					model.addAttribute("description", imageGallery.get().getDescription());
					model.addAttribute("name", imageGallery.get().getName());
					model.addAttribute("numbervideo", imageGallery.get().getVideonumber());
					return "myvideodetails";
				}
				return "redirect:/homevideo";
			}
		return "redirect:/homevideo";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/homevideo";
		}	
	}

	@GetMapping("/video/show")
	String show(Model map) {
		List<MyVideo> images = imageGalleryService.getAllActiveImages();
		map.addAttribute("images", images);
		return "videos";
	}

	
	// compress the image bytes before storing it in the database
		public static byte[] compressBytes(byte[] data) {
			Deflater deflater = new Deflater();
			deflater.setInput(data);
			deflater.finish();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			while (!deflater.finished()) {
				int count = deflater.deflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			try {
				outputStream.close();
			} catch (IOException e) {
			}
			System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
			return outputStream.toByteArray();
		}
		// uncompress the image bytes before returning it to the angular application
		public static byte[] decompressBytes(byte[] data) {
			Inflater inflater = new Inflater();
			inflater.setInput(data);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			try {
				while (!inflater.finished()) {
					int count = inflater.inflate(buffer);
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
			} catch (IOException ioe) {
			} catch (DataFormatException e) {
			}
			return outputStream.toByteArray();
		}

}
