package com.AmazonClone.Seller.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AmazonClone.Seller.Model.Delivery;
import com.AmazonClone.Seller.Repository.DeliveryRepo;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepo deliveryRepo;

    public Delivery saveDelivery(Delivery address) {
        return deliveryRepo.save(address);
    }
}
