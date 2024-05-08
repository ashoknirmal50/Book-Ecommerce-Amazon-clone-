package com.AmazonClone.Seller.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AmazonClone.Seller.Dto.BillDTO;
import com.AmazonClone.Seller.Dto.CartItemDTO;
import com.AmazonClone.Seller.Model.CartItem;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillingService {
    @Autowired
    private CartItemService cartItemService;

    public BillDTO generateBill() {
        List<CartItem> cartItems = cartItemService.getAllCartItems();
        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
        double totalBill = 0.0;

        for (CartItem cartItem : cartItems) {
            double itemPrice = cartItem.getBook().getPrice() * cartItem.getQuantity();
            totalBill += itemPrice;
            CartItemDTO cartItemDTO = new CartItemDTO(cartItem.getBook().getTitle(), cartItem.getQuantity(), itemPrice);
            cartItemDTOs.add(cartItemDTO);
        }

        return new BillDTO(cartItemDTOs, totalBill);
    }
}

