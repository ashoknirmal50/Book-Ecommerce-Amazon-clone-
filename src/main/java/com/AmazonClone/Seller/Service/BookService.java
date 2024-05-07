package com.AmazonClone.Seller.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AmazonClone.Seller.Model.Book;
import com.AmazonClone.Seller.Repository.BookRepository;

<<<<<<< HEAD
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    //to get all the books
=======
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
public class BookService {
 
    @Autowired
    private BookRepository bookRepository;
 
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
                default:
                    // Handle additional columns if needed
            }
            cellIndex++;
        }
        bookRepository.save(book);
    }

    // Get all books
>>>>>>> 70aded520a8a1016f1376510eef7adc4b104ee38
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
