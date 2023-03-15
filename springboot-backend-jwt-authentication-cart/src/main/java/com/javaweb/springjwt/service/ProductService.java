package com.javaweb.springjwt.service;

import com.javaweb.springjwt.models.Product;
import com.javaweb.springjwt.payload.request.CreateProductRequest;
import com.javaweb.springjwt.payload.response.MessageResponse;
import com.javaweb.springjwt.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final FilesStorageService filesStorageService;

    @Autowired
    public ProductService(ProductRepository productRepository, FilesStorageService filesStorageService) {
        this.productRepository = productRepository;
        this.filesStorageService = filesStorageService;
    }

    public ResponseEntity<?> getAll() {
        List<Product> productList = this.productRepository.findAll();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    public ResponseEntity<?> create(CreateProductRequest request, MultipartFile file) {
       int status = this.filesStorageService.save(file);
        if(status != 0) {
            Product product = new Product();
            product.setName(request.getName().trim());
            product.setPrice(request.getPrice());
            product.setPhoto(file.getOriginalFilename());
            this.productRepository.save(product);
        }
        else {
            throw new RuntimeException("Upload failed");
        }
        return new ResponseEntity<>(new MessageResponse("Product is created"), HttpStatus.OK);
    }
    public ResponseEntity<?> updateById(CreateProductRequest request, MultipartFile file, Long id) throws IOException {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
            int status = this.filesStorageService.save(file);
            if(status != 0) {
                product.setPhoto(file.getOriginalFilename());
            }
            else {
                throw new RuntimeException("Upload failed");
            }
        if(!product.getName().equals(request.getName())) {
            product.setName(request.getName());
        }
        if(!product.getPrice().equals(request.getPrice())) {
            product.setPrice(request.getPrice());
        }
        this.productRepository.save(product);
        return new ResponseEntity<>(new MessageResponse("Product is updated"), HttpStatus.OK);
    }
    public ResponseEntity<?> findById(Long id) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    public ResponseEntity<?> deleteById(Long id ){
            if(!this.productRepository.existsById(id)) {
                throw new RuntimeException("Product not found");
            }
            this.productRepository.deleteById(id);
            return new ResponseEntity<>(new MessageResponse("Product is deleted"), HttpStatus.OK);
    }
}
