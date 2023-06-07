package com.microservices.ProductService.service;

import com.microservices.ProductService.model.ProductRequest;
import com.microservices.ProductService.model.ProductResponse;

public interface ProductService {
    long addproduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    public void reduceQuantity(long productId, long quantity);
}
