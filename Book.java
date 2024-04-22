package com.AmazonClone.Seller.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Data
@Table(name = "Books")
public class Book {
    
    @Id
    @GeneratedValue
    @Column (name = "bookId")
    private int bookId;

    @Column(name = "isbn")
    // ISBN of the book, ensuring it is not blank
    @NotBlank(message = "ISBN cannot be blank") 
    private String isbn;
    
    @Column(name = "title")
    // Title of the book, ensuring it is not blank
    @NotBlank(message = "Title cannot be blank") 
    private String title;
    
    @Column(name = "author")
    // Author of the book, ensuring it is not blank
    @NotBlank(message = "Author cannot be blank") 
    private String author;
    
    @Column(name = "description")
    // Description of the book (optional)
    private String description; 
    
    @Column(name = "edition")
    // Edition of the book, ensuring it is a positive integer
    @Positive(message = "Edition must be positive") 
    private int edition;
    
    @Column(name = "publisher")
    // Publisher of the book (optional)
    private String publisher;
    
    @Column(name = "quantity")
    // Quantity of the book in stock, ensuring it is not null and positive
    @NotNull(message = "Quantity cannot be null") 
    @Positive(message = "Quantity must be positive") 
    private int quantity;
    
    @Column(name = "price")
    // Price of the book, ensuring it is not null and greater than or equal to 10.0
    @NotNull(message = "Price cannot be null") 
    @DecimalMin(value = "10.0", inclusive = true, message = "Price must be greater than 10") 
    private double price;

    public Book(){
        
    }

    public Book(@NotBlank(message = "ISBN cannot be blank") String isbn,
            @NotBlank(message = "Title cannot be blank") String title,
            @NotBlank(message = "Author cannot be blank") String author, String description,
            @Positive(message = "Edition must be positive") int edition, String publisher,
            @NotNull(message = "Quantity cannot be null") @Positive(message = "Quantity must be positive") int quantity,
            @NotNull(message = "Price cannot be null") @DecimalMin(value = "10.0", inclusive = true, message = "Price must be greater than 10") double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.description = description;
        this.edition = edition;
        this.publisher = publisher;
        this.quantity = quantity;
        this.price = price;
    }
}