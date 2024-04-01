package com.AmazonClone.Seller.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Data
public class Book {
    
    @Id
    @GeneratedValue
    private int bookId;

    // ISBN of the book, ensuring it is not blank
    @NotBlank(message = "ISBN cannot be blank") 
    private String isbn;
    
    // Title of the book, ensuring it is not blank
    @NotBlank(message = "Title cannot be blank") 
    private String title;
    
    // Author of the book, ensuring it is not blank
    @NotBlank(message = "Author cannot be blank") 
    private String author;
    
    // Description of the book (optional)
    private String description; 

    // Edition of the book, ensuring it is a positive integer
    @Positive(message = "Edition must be positive") 
    private int edition;

    // Publisher of the book (optional)
    private String publisher;
    
    // Quantity of the book in stock, ensuring it is not null and positive
    @NotNull(message = "Quantity cannot be null") 
    @Positive(message = "Quantity must be positive") 
    private int quantity;
    
    // Price of the book, ensuring it is not null and greater than or equal to 10.0
    @NotNull(message = "Price cannot be null") 
    @DecimalMin(value = "10.0", inclusive = true, message = "Price must be greater than 10") 
    private double price;
}
