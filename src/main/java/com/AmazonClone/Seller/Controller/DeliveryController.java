package com.AmazonClone.Seller.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.AmazonClone.Seller.Model.Delivery;
import com.AmazonClone.Seller.Service.DeliveryService;



@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    //get address from the user
    @PostMapping("/capture")
    public Delivery captureAddress(@RequestBody Delivery address) {
        return deliveryService.saveDelivery(address);
    }
}


// {
//     "street" : "loosu street",
//     "city" : "Chennai",
//     "zipCode": "600125"
//   }