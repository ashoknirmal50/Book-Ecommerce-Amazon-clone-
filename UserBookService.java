package com.AmazonClone.Seller.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AmazonClone.Seller.Model.Book;
import com.AmazonClone.Seller.Repository.UserBookRepository;

@Service
public class UserBookService {

    @Autowired
    private UserBookRepository userBookRepository;

    // Search books by title
    public List<Book> searchByTitle(String title) {
        return userBookRepository.findByTitleContainingIgnoreCase(title);
    }

    // Search books by author
    public List<Book> searchByAuthor(String author) {
        return userBookRepository.findByAuthorContainingIgnoreCase(author);
    }

    // Search books by title and author
    public List<Book> searchBooksByTitleAndAuthor(String title, String author) {
        return userBookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author);
    }

    // Search books by title and edition
    public List<Book> searchBooksByTitleAndEdition(String title, int edition) {
        return userBookRepository.findByTitleContainingIgnoreCaseAndEdition(title, edition);
    }

    // Search for books by title, author, and edition
    public List<Book> searchByTitleAuthorAndEdition(String title, String author, Integer edition) {
       if (title != null && author != null && edition != null) {
           return userBookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndEdition(title, author, edition);
       } else if (title != null && edition != null) {
           return userBookRepository.findByTitleContainingIgnoreCaseAndEdition(title, edition);
       } else if (title != null && author != null) {
           return userBookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author);
       } else if (title != null) {
           return userBookRepository.findByTitleContainingIgnoreCase(title);
       } else if (author != null) {
           return userBookRepository.findByAuthorContainingIgnoreCase(author);
       } else {
           return userBookRepository.findAll();
       }
    }

}
