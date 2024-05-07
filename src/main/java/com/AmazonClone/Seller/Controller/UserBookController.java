package com.AmazonClone.Seller.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.AmazonClone.Seller.Model.Book;
import com.AmazonClone.Seller.Service.UserBookService;

@RestController
@RequestMapping("/user")
public class UserBookController {

    @Autowired
    private UserBookService userBookService;
        
    // Search for books by title
    @GetMapping("/search/title")
    public ResponseEntity<?> searchByTitle(@RequestParam String title) {
        List<Book> books = userBookService.searchByTitle(title);
            if (books.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books found with title: " + title);
    }
    return ResponseEntity.ok().body(books);
}

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = userBookService.getAllBooks();
        return ResponseEntity.ok().body(books);
    }
    // Search for books by author
    @GetMapping("/search/author")
public ResponseEntity<?> searchByAuthor(@RequestParam String author) {
    List<Book> books = userBookService.searchByAuthor(author);
    if (books.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books found by author: " + author);
    }
    return ResponseEntity.ok().body(books);
}

@GetMapping("/search/TitleAndAuthor")
public ResponseEntity<?> searchBooksByTitleAndAuthor(@RequestParam String title, @RequestParam String author) {
    List<Book> books = userBookService.searchBooksByTitleAndAuthor(title, author);
    if (books.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books found with title: " + title + " and author: " + author);
    }
    return ResponseEntity.ok().body(books);
}

@GetMapping("/search/TitleAndEdition")
public ResponseEntity<?> searchBooksByTitleAndEdition(@RequestParam String title, @RequestParam int edition) {
    List<Book> books = userBookService.searchBooksByTitleAndEdition(title, edition);
    if (books.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books found with title: " + title + " and edition: " + edition);
    }
    return ResponseEntity.ok().body(books);
}

@GetMapping("/search/TitleAuthorAndEdition")
public ResponseEntity<?> searchByTitleAuthorAndEdition(@RequestParam String title, @RequestParam String author, @RequestParam int edition) {
    List<Book> books = userBookService.searchByTitleAuthorAndEdition(title, author, edition);
    if (books.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books found with title: " + title + ", author: " + author + " and edition: " + edition);
    }
    return ResponseEntity.ok().body(books);
}

// Search for books by genre
@GetMapping("/search/genre")
public ResponseEntity<?> searchByGenre(@RequestParam String genre) {
    List<Book> books = userBookService.searchByGenre(genre);
    if (books.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books found with genre: " + genre);
    }
    return ResponseEntity.ok().body(books);
}

}
