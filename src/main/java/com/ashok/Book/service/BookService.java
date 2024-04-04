package com.ashok.Book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashok.Book.model.Book;
import com.ashok.Book.repository.BookRepo;

@Service
public class BookService {


    @Autowired
    private BookRepo bookRepo;

    public List<Book> getAllBooks() {
        return bookRepo.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepo.findById(id).orElse(null);
    }

    public Book createBook(Book book) {
        return bookRepo.save(book);
    }

    public Book updateBook(Long id, Book book) {
        if (bookRepo.existsById(id)) {
            book.setId(id);
            return bookRepo.save(book);
        }
        return null;
    }

    public void deleteBook(Long id) {
        bookRepo.deleteById(id);
    }
}
