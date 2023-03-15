package com.javaweb.springjwt.service;

import com.javaweb.springjwt.models.Cart;
import com.javaweb.springjwt.payload.request.CreateCartRequest;
import com.javaweb.springjwt.payload.response.CartResponse;
import com.javaweb.springjwt.payload.response.MessageResponse;
import com.javaweb.springjwt.payload.response.ProductResponse;
import com.javaweb.springjwt.repository.CartRepository;
import com.javaweb.springjwt.repository.ProductRepository;
import com.javaweb.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service

public class CartService {


    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> getAllByUserId(Long userid) {
        List<Cart> carts = this.cartRepository.findAllByUserId(userid);
        List<CartResponse> responses = new ArrayList<>();
        for (Cart cart : carts) {
            ProductResponse productResponse = new ProductResponse(cart.getProduct().getName(), cart.getProduct().getPrice(), cart.getProduct().getPhoto());
            CartResponse cartResponse = new CartResponse(cart.getId(), productResponse, cart.getQuantity());
            responses.add(cartResponse);
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    public ResponseEntity<?> create(Long userId, CreateCartRequest request) {
        Cart availableCart = this.cartRepository.findAllByUserIdAndProductId(userId, request.getProductId());
        if (availableCart != null) {
            Integer quantity = availableCart.getQuantity();
            quantity += request.getQuantity();
            availableCart.setQuantity(quantity);
            this.cartRepository.save(availableCart);
        } else {
            Cart cartItem = new Cart();
            cartItem.setUser(this.userRepository.findById(userId).orElse(null));
            cartItem.setProduct(this.productRepository.findById(request.getProductId()).orElse(null));
            cartItem.setQuantity(request.getQuantity());
            this.cartRepository.save(cartItem);
        }
        return new ResponseEntity<>(new MessageResponse("Cart is created"), HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<?> deteleById(Long id, Long userId) {
        if (!this.cartRepository.existsById(id)) {
            throw new RuntimeException("Không tồn tại");
        }
        this.cartRepository.deleteByIdAndUserId(id, userId);
        return new ResponseEntity<>(new MessageResponse("Cart is deleted"), HttpStatus.OK);
    }

}
