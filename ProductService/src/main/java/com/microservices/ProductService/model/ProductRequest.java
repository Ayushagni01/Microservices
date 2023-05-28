package com.microservices.ProductService.model;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ProductRequest {

    private String name;
    private long price;
    private long quantity;
}
