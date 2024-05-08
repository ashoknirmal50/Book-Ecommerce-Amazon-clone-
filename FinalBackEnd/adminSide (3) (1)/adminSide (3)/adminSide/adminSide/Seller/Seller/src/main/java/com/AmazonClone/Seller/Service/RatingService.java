package com.AmazonClone.Seller.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AmazonClone.Seller.Model.Rating;
import com.AmazonClone.Seller.Repository.RatingRepo;


@Service
public class RatingService {

    @Autowired
    private RatingRepo bookRepo;

    public List<Rating> getAllBooks() {
        return bookRepo.findAll();
    }

    public Rating getBookById(Long id) {
        return bookRepo.findById(id).orElse(null);
    }

    public Rating createBook(Rating book) {
        return bookRepo.save(book);
    }

    public Rating updateBook(Long id, Rating book) {
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
