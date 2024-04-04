package com.ashok.Book.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String genre;
    private double rating;
    private String review;
    
    // public String getTitle() {
    //     return title;
    // }
    // public void setTitle(String title) {
    //     this.title = title;
    // }
    // public String getAuthor() {
    //     return author;
    // }
    // public void setAuthor(String author) {
    //     this.author = author;
    // }
    // public String getGenre() {
    //     return genre;
    // }
    // public void setGenre(String genre) {
    //     this.genre = genre;
    // }
    // public double getRating() {
    //     return rating;
    // }
    // public void setRating(double rating) {
    //     this.rating = rating;
    // }
    // public String getReview() {
    //     return review;
    // }
    // public void setReview(String review) {
    //     this.review = review;
    // }
}
