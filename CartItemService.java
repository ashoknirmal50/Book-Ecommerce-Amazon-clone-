package com.AmazonClone.Seller.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AmazonClone.Seller.Model.Book;
import com.AmazonClone.Seller.Model.CartItem;
import com.AmazonClone.Seller.Repository.CartItemRepository;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    //method to add item to the cart
    public void addToCart(Book book, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    //To update the cart item
    public boolean updateCartItem(CartItem cartItem) {
        // Check if the cart item exists before attempting to update it
        if (cartItemRepository.existsById(cartItem.getId())) {
            cartItemRepository.save(cartItem);
            return true; // Update successful
        } else {
            return false; // Cart item not found, update failed
        }
    }

    //To delete the cart item
    public boolean deleteCartItem(Long cartItemId) {
        // Check if the cart item exists before attempting to delete it
        if (cartItemRepository.existsById(cartItemId)) {
            cartItemRepository.deleteById(cartItemId);
            return true; // Deletion successful
        } else {
            return false; // Cart item not found, deletion failed
        }
    }
}

