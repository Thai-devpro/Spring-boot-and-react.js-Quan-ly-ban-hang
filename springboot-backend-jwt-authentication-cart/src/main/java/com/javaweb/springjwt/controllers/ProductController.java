package com.javaweb.springjwt.controllers;

import com.javaweb.springjwt.payload.request.CreateProductRequest;
import com.javaweb.springjwt.service.FilesStorageService;
import com.javaweb.springjwt.service.ProductService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequestMapping("/api/product")

public class ProductController {
    private final ProductService productService;
    private final FilesStorageService filesStorageService;
    @Autowired
    public ProductController(ProductService productService, FilesStorageService filesStorageService) {
        this.productService = productService;
        this.filesStorageService = filesStorageService;
    }
    @GetMapping("list")
    public ResponseEntity<?> getAll() {
        return this.productService.getAll();
    }



    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return this.productService.findById(id);
    }



    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateById(@Valid @RequestPart CreateProductRequest request,
                                        @RequestPart MultipartFile file,
                                        @PathVariable Long id) throws IOException {
        return this.productService.updateById(request,file, id);
    }



    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestPart CreateProductRequest request, @RequestPart MultipartFile file) {
        return this.productService.create(request,file);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return this.productService.deleteById(id);
    }


    @GetMapping("/image/{filename}")
    public ResponseEntity<?> getImage(@PathVariable("filename") String filename) {
        Resource resource = this.filesStorageService.load(filename);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
    }


}
