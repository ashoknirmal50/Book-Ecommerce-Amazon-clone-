package com.AmazonClone.Seller.Service;

import com.AmazonClone.Seller.Model.Book;
import com.AmazonClone.Seller.Repository.BookRepository;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

     // Add books from Excel file
    public List<Book> addBooksFromExcel(MultipartFile file) throws IOException {
        List<Book> books = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
        
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header row
            
            Book book = new Book();
            book.setIsbn(row.getCell(0).getStringCellValue());
            book.setTitle(row.getCell(1).getStringCellValue());
            book.setAuthor(row.getCell(2).getStringCellValue());
            book.setDescription(row.getCell(3).getStringCellValue());
            book.setEdition((int) row.getCell(4).getNumericCellValue());
            book.setPublisher(row.getCell(5).getStringCellValue());
            book.setQuantity((int) row.getCell(6).getNumericCellValue());
            book.setPrice(row.getCell(7).getNumericCellValue());

            books.add(book);
        }

       workbook.close();
        // Save all books to the database
        return bookRepository.saveAll(books);
    }

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get book by ID
    public Optional<Book> getBookById(int bookId) {
        return bookRepository.findById(bookId);
    }

    // Add a new book
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // Update an existing book
    public Optional<Book> updateBook(int bookId, Book bookDetails) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setDescription(bookDetails.getDescription());
            book.setEdition(bookDetails.getEdition());
            book.setPublisher(bookDetails.getPublisher());
            book.setQuantity(bookDetails.getQuantity());
            book.setPrice(bookDetails.getPrice());
            return Optional.of(bookRepository.save(book));
        } else {
            return Optional.empty();
       }
    }

    // Delete a book
    public boolean deleteBook(int bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            bookRepository.delete(book.get());
            return true;
        } else {
            return false;
        }
    }

    // Search for books by title, author, and edition
    public List<Book> searchBooks(String title, String author, Integer edition) {
        if (title != null && author != null && edition != null) {
            return bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndEdition(title, author, edition);
        } else if (title != null && edition != null) {
            return bookRepository.findByTitleContainingIgnoreCaseAndEdition(title, edition);
        } else if (title != null && author != null) {
            return bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author);
        } else if (title != null) {
            return bookRepository.findByTitleContainingIgnoreCase(title);
        } else if (author != null) {
            return bookRepository.findByAuthorContainingIgnoreCase(author);
        } else {
            return bookRepository.findAll();
        }
    }
}
