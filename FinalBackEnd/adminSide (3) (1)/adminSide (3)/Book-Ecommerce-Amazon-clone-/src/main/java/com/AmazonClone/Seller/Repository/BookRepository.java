package com.AmazonClone.Seller.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AmazonClone.Seller.Model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

    // Search by book title
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    // Search by book author
    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(String title, String author);

    // Search by book title, author, and edition
    List<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndEdition(String title, String author, int edition);
    
    // Search by book title and edition
    List<Book> findByTitleContainingIgnoreCaseAndEdition(String title, int edition);
}
