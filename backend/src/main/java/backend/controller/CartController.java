package backend.controller;

import backend.model.CartItem;
import backend.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartItemRepository cartItemRepo;

    @PostMapping("/save")
    public String saveCartItems(@RequestBody List<CartItem> cartItems) {
        cartItemRepo.saveAll(cartItems);
        return "Cart saved successfully";
    }

    @GetMapping("/orders/{userId}")
    public List<CartItem> getUserOrders(@PathVariable Long userId) {
        return cartItemRepo.findByUserId(userId);
    }
    // Get all orders
    @GetMapping("/orders")
    public List<CartItem> getAllOrders() {
        return cartItemRepo.findAll();
    }

    // Delete order
    @DeleteMapping("/orders/{id}")
    public String deleteOrder(@PathVariable Long id) {
        cartItemRepo.deleteById(id);
        return "Order deleted";
    }


}
