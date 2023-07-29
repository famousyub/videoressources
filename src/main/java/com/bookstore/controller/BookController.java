package com.bookstore.controller;

import com.bookstore.entity.BookVideo;
import com.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BookVideo addBook(@RequestBody BookVideo book) {
      return  bookService.save(book);
    }

    @RequestMapping(value = "/add/image", method = RequestMethod.POST)
    public ResponseEntity uploadImage(@RequestParam("id") int id, HttpServletResponse response, HttpServletRequest request) {
        try {
        BookVideo book = bookService.findOne(id);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator<String> it = multipartRequest.getFileNames();
        MultipartFile multipartFile = multipartRequest.getFile(it.next());
        String fileName = id+".png";

        byte[] bytes = multipartFile.getBytes();
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/book/"+ fileName)));
        stream.write(bytes);
        stream.close();
            return new ResponseEntity("Upload Success!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Upload Failed!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/bookList")
    public List<BookVideo> getBookList() {
      return bookService.findAll();
    }

    @RequestMapping("/{id}")
    public BookVideo getBook(@PathVariable("id") int id) {
        BookVideo book = bookService.findOne(id);
        return book;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public BookVideo updateBook(@RequestBody BookVideo book) {
        return bookService.save(book);
    }

    @RequestMapping(value = "/update/image", method = RequestMethod.POST)
    public ResponseEntity updateImage(@RequestParam("id") int id, HttpServletRequest request, HttpServletResponse response) {
        try {
            BookVideo book = bookService.findOne(id);
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Iterator<String> it = multipartRequest.getFileNames();
            MultipartFile multipartFile = multipartRequest.getFile(it.next());
            String fileName = id+".png";

            Files.delete(Paths.get("src/main/resources/static/image/book/"+ fileName));

            byte[] bytes = multipartFile.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/book/"+ fileName)));
            stream.write(bytes);
            stream.close();
            return new ResponseEntity("Upload Success!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Upload Failed!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ResponseEntity removeBook(@RequestBody String id) throws IOException {
        bookService.removeOne(Integer.parseInt(id));
        String fileName = id+".png";
        Files.delete(Paths.get("src/main/resources/static/image/book/"+ fileName));
        return new ResponseEntity("Remove Success!", HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<BookVideo> searchByTitle(@RequestBody String keyword) {
        List<BookVideo> bookList = bookService.searchByTitle(keyword);
        return bookList;
    }
}
