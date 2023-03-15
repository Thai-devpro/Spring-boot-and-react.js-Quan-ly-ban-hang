package com.javaweb.springjwt.payload.response;

import com.javaweb.springjwt.models.Product;

public class CartResponse {
    private Long id;
    private ProductResponse product;
    private Integer quantity;

    public CartResponse(Long id, ProductResponse product, Integer quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public void setProduct(ProductResponse product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
