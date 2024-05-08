package com.AmazonClone.Seller.Dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private String productName;
    private int quantity;
    private double price;

    public CartItemDTO(String productName, int quantity, double price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
}

