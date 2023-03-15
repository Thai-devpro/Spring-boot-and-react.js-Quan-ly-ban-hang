package com.javaweb.springjwt.payload.response;

import java.math.BigDecimal;

public class ProductResponse {
    private String name;
    private BigDecimal price;
    private String photo;

    public ProductResponse(String name, BigDecimal price, String photo) {
        this.name = name;
        this.price = price;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
