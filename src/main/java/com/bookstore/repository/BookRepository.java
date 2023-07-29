package com.bookstore.repository;

import com.bookstore.entity.BookVideo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<BookVideo, Integer> {
    List<BookVideo> findByTitleContaining(String title);
}
