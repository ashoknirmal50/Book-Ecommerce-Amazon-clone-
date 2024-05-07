package com.AmazonClone.Seller.Service;

import com.AmazonClone.Seller.Model.Book;
import com.AmazonClone.Seller.Repository.AdminBookRepository;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AdminBookService {

    @Autowired
    private AdminBookRepository bookRepository;

      // Add books from Excel file with multithreading
      public void addBooksFromExcel(InputStream inputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
        Iterator<Row> rows = sheet.iterator();
 
        // Skipping the header row assuming it contains column names
        if (rows.hasNext()) {
            rows.next();
        }
 
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(availableProcessors);
 
        while (rows.hasNext()) {
            Row currentRow = rows.next();
            executorService.execute(() -> {
                // Process each row concurrently
                processRow(currentRow);
            });
        }
 
        executorService.shutdown();
        workbook.close();
    }
 
    // Process a single row from Excel file
    private void processRow(Row currentRow) {
        Iterator<Cell> cellsInRow = currentRow.iterator();
        Book book = new Book();
        int cellIndex = 0;
        while (cellsInRow.hasNext()) {
            Cell currentCell = cellsInRow.next();
            switch (cellIndex) {
                case 0:
                    book.setIsbn(currentCell.getStringCellValue());
                    break;
                case 1:
                    book.setTitle(currentCell.getStringCellValue());
                    break;
                case 2:
                    book.setAuthor(currentCell.getStringCellValue());
                    break;
                case 3:
                    book.setDescription(currentCell.getStringCellValue());
                    break;
                case 4:
                    book.setEdition((int) currentCell.getNumericCellValue());
                    break;
                case 5:
                    book.setPublisher(currentCell.getStringCellValue());
                    break;
                case 6:
                    book.setQuantity((int) currentCell.getNumericCellValue());
                    break;
                case 7:
                    book.setPrice(currentCell.getNumericCellValue());
                    break;
                case 8:
                    book.setGenre(currentCell.getStringCellValue()); 
                    break;
                case 9:
                    book.setImageUrl(currentCell.getStringCellValue());
                    break;
                default:
                    // Handle additional columns if needed
            }
            cellIndex++;
        }
        bookRepository.save(book);
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
            book.setGenre(bookDetails.getGenre());
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
}
