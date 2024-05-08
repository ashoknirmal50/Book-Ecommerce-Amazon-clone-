package com.AmazonClone.Seller.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AmazonClone.Seller.Model.Book;
import com.AmazonClone.Seller.Repository.BookRepository;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    //to get all the books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    //To retrive books by Id
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    //To update a book
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }
}
