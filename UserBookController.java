package com.AmazonClone.Seller.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.AmazonClone.Seller.Model.Book;
import com.AmazonClone.Seller.Service.UserBookService;

@RestController
public class UserBookController {

    @Autowired
    private UserBookService userBookService;
        
    // Search for books by title
    @GetMapping("/search/title")
    public ResponseEntity<?> searchByTitle(@RequestBody Map<String, String> requestBody) {
        String title = requestBody.get("title");
        List<Book> books = userBookService.searchByTitle(title);
        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books found with title: " + title);
        }
        return ResponseEntity.ok().body(books);
    }
    
    // Search for books by author
    @GetMapping("/search/author")
    public ResponseEntity<?> searchByAuthor(@RequestBody Map<String, String> requestBody) {
        String author = requestBody.get("author");
        List<Book> books = userBookService.searchByAuthor(author);
        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books found by author: " + author);
        }
        return ResponseEntity.ok().body(books);
    }

    //search book by title and author
    @GetMapping("/search/TitleAndAuthor")
    public ResponseEntity<?> searchBooksByTitleAndAuthor(@RequestBody Map<String, String> requestBody) {
        String title = requestBody.get("title");
        String author = requestBody.get("author");
        List<Book> books = userBookService.searchBooksByTitleAndAuthor(title, author);
        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books found with title: " + title + " and author: " + author);
        }
        return ResponseEntity.ok().body(books);
    }

    // Search for books by title and edition
    @GetMapping("/search/TitleAndEdition")
    public ResponseEntity<?> searchBooksByTitleAndEdition(@RequestBody Map<String, Object> requestBody) {
        String title = (String) requestBody.get("title");
        int edition = (int) requestBody.get("edition");
        List<Book> books = userBookService.searchBooksByTitleAndEdition(title, edition);
        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books found with title: " + title + " and edition: " + edition);
        }
        return ResponseEntity.ok().body(books);
    }

    // Search for books by title, author, and edition
    @GetMapping("/search/TitleAuthorAndEdition")
    public ResponseEntity<?> searchByTitleAuthorAndEdition(@RequestBody Map<String, Object> requestBody) {
        String title = (String) requestBody.get("title");
        String author = (String) requestBody.get("author");
        int edition = (int) requestBody.get("edition");      
        List<Book> books = userBookService.searchByTitleAuthorAndEdition(title, author, edition);
        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books found with title: " + title + ", author: " + author + " and edition: " + edition);
        }
        return ResponseEntity.ok().body(books);
    }
}
