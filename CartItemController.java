package com.AmazonClone.Seller.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.AmazonClone.Seller.Dto.BillDTO;
import com.AmazonClone.Seller.Model.Book;
import com.AmazonClone.Seller.Model.CartItem;
import com.AmazonClone.Seller.Service.BillingService;
import com.AmazonClone.Seller.Service.BookService;
import com.AmazonClone.Seller.Service.CartItemService;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BillingService billingService;

    @PostMapping("/add")
public ResponseEntity<String> addToCart(@RequestParam Long bookId, @RequestParam int quantity) {
    Book book = bookService.getBookById(bookId);
    if (book != null) {
        if (book.getQuantity() >= quantity) {
            // Reduce the quantity of the book in the database
            int remainingQuantity = book.getQuantity() - quantity;
            book.setQuantity(remainingQuantity);
            bookService.updateBook(book); // Update the book in the database

            // Add the item to the cart
            cartItemService.addToCart(book, quantity);

            String successMessage = "Added " + quantity + " " + book.getTitle() + " book to the cart.";
            return ResponseEntity.ok(successMessage);
        } else {
            String errorMessage = "Requested quantity exceeds available stock. Only " + book.getQuantity() + " items are available.";
            return ResponseEntity.badRequest().body(errorMessage);
        }
    } else {
        return ResponseEntity.badRequest().body("Book not found");
    }
}


    //get all the items in the cart
    @GetMapping("/items")
    public List<CartItem> getAllCartItems() {
        return cartItemService.getAllCartItems();
    }

    //update an item in the cart
    @PutMapping("/update")
    public ResponseEntity<String> updateCartItem(@RequestBody CartItem cartItem) {  
        boolean updated = cartItemService.updateCartItem(cartItem);
        if (updated) {
            return ResponseEntity.ok("Cart item updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to update cart item. Please check the provided details.");
        }
    }

    //remove an item from the cart
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long cartItemId) {
        boolean deleted = cartItemService.deleteCartItem(cartItemId);
        if (deleted) {
            return ResponseEntity.ok("Cart item deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete cart item. Please check the provided cart item ID.");
        }
    }

    //To generate the bill for list of products available in the cart
    @GetMapping("/bill")
    public ResponseEntity<BillDTO> getBill() {
        BillDTO billDTO = billingService.generateBill();
        return ResponseEntity.ok(billDTO);
    }
}

