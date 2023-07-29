package com.bookstore.service;

import com.bookstore.entity.BookVideo;

import java.util.List;

public interface BookService {

    BookVideo save(BookVideo book);
    BookVideo findOne(int id);
    List<BookVideo> findAll();
    List<BookVideo> searchByTitle(String title);
    void removeOne(int id);

}
