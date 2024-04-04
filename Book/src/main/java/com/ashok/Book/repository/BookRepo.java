package com.ashok.Book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashok.Book.model.Book;

public interface BookRepo extends JpaRepository<Book, Long>{
    
}
