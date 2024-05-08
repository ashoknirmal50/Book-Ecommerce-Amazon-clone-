package com.AmazonClone.Seller.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AmazonClone.Seller.Model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>{
    
}
