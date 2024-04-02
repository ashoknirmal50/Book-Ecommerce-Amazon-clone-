package com.AmazonClone.Seller.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.AmazonClone.Seller.Model.Book;
import com.AmazonClone.Seller.Service.BookService;

@RestController
@RequestMapping("/Seller")
public class BookController {

    @Autowired
    private BookService bookService;
    
    @PostMapping("/upload/books")
    public ResponseEntity<String> uploadBooksFromExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file!", HttpStatus.BAD_REQUEST);
        }

        try {
            InputStream inputStream = file.getInputStream();
            bookService.addBooksFromExcel(inputStream);
            inputStream.close();
            return new ResponseEntity<>("File uploaded successfully!", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload file!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok().body(books);
    }

    // Get book by ID
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable int bookId) {
        Optional<Book> book = bookService.getBookById(bookId);
        if (book.isPresent()) {
            return ResponseEntity.ok().body(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Add a new book
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book newBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    // Update an existing book
    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable int bookId, @RequestBody Book bookDetails) {
        Optional<Book> updatedBook = bookService.updateBook(bookId, bookDetails);
        if (updatedBook.isPresent()) {
            return ResponseEntity.ok().body(updatedBook.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a book
    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable int bookId) {
        boolean deleted = bookService.deleteBook(bookId);
        if (deleted) {
            return ResponseEntity.ok().body("Book deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Search for books by title, author, and edition
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false) String title,
                                                   @RequestParam(required = false) String author,
                                                   @RequestParam(required = false) Integer edition) {
        List<Book> books = bookService.searchBooks(title, author, edition);
        return ResponseEntity.ok().body(books);
    }
}
