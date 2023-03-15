package com.javaweb.springjwt.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.springjwt.models.Product;
import com.javaweb.springjwt.repository.ProductRepository;


@RestController
@RequestMapping("/api/test")
public class TestController { 
	@Autowired
	ProductRepository productRepository;

  @GetMapping("/user")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
  public String userAccess() {
    return "User page: Shopping.";
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('ROLE_MODERATOR')")	
  public String moderatorAccess() {
    return "Moderator board: CRUD.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ROLE_ADMIN')") 
  public String adminAccess() {
    return "Admin board: Manage users and CRUD.";
  }
}
