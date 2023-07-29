package com.bookstore.service.impl;

import com.bookstore.entity.BookVideo;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookVideo save(BookVideo book) {
        return bookRepository.save(book);
    }

    @Override
    public BookVideo findOne(int id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public List<BookVideo> findAll() {
         List<BookVideo> bookList = (List<BookVideo>) bookRepository.findAll();

         List<BookVideo> activeBookList = new ArrayList<>();

         for (BookVideo book: bookList){
             if (book.isActive()){
                 activeBookList.add(book);
             }
         }
         return activeBookList;
    }

    @Override
    public List<BookVideo> searchByTitle(String title) {
        List<BookVideo> bookList = bookRepository.findByTitleContaining(title);

        List<BookVideo> activeBookList = new ArrayList<>();
        for (BookVideo book: bookList){
            if (book.isActive()){
                activeBookList.add(book);
            }
        }
        return activeBookList;
    }

    @Override
    public void removeOne(int id) {
        bookRepository.deleteById(id);;
    }
}
