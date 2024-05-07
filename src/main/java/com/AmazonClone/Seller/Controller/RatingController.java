package com.AmazonClone.Seller.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.AmazonClone.Seller.Model.Rating;
import com.AmazonClone.Seller.Service.RatingService;


import java.util.List;

@RestController
@RequestMapping("/api/books")
public class RatingController {
    
    @Autowired
    private RatingService bookService;

    @GetMapping
    public ResponseEntity<List<Rating>> getAllBooks() {
        List<Rating> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getBookById(@PathVariable Long id) {
        Rating book = bookService.getBookById(id);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Rating> createBook(@RequestBody Rating book) {
        Rating createdBook = bookService.createBook(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rating> updateBook(@PathVariable Long id, @RequestBody Rating book) {
        Rating updatedBook = bookService.updateBook(id, book);
        if (updatedBook != null) {
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


// {
//     "title" : "I LOVE YOU",
//     "author" : "Rishab",
//     "genre": "Romance",
//     "rating": 0,
//     "review": "Worst Book Ever"
//   }