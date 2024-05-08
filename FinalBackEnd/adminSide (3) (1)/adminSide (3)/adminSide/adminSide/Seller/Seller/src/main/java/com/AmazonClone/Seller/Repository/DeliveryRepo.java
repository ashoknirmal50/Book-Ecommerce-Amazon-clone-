package com.AmazonClone.Seller.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AmazonClone.Seller.Model.Delivery;

public interface DeliveryRepo extends JpaRepository<Delivery,Long> {
    
}
