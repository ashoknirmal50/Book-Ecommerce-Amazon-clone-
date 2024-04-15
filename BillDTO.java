package com.AmazonClone.Seller.Dto;

import lombok.Data;

import java.util.List;

@Data
public class BillDTO {
    private List<CartItemDTO> billItems;
    private double totalBill;

    public BillDTO(List<CartItemDTO> billItems, double totalBill) {
        this.billItems = billItems;
        this.totalBill = totalBill;
    }
}

