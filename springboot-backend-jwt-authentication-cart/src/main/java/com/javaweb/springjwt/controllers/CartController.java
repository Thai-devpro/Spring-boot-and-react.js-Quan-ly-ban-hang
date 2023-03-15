package com.javaweb.springjwt.controllers;

import com.javaweb.springjwt.payload.request.CreateCartRequest;
import com.javaweb.springjwt.security.services.UserDetailsImpl;
import com.javaweb.springjwt.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("list")
    @PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
    public ResponseEntity<?> getAllByUserId(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return this.cartService.getAllByUserId(userDetails.getId());
    }

    @PostMapping()
    @PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
    public ResponseEntity<?> create(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @Valid @RequestBody CreateCartRequest request) {
        return this.cartService.create(userDetails.getId(), request);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return this.cartService.deteleById(id, userDetails.getId());
    }
}
