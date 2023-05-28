package com.microservices.ProductService.service;

import com.microservices.ProductService.entity.Product;
import com.microservices.ProductService.model.ProductRequest;
import com.microservices.ProductService.model.ProductResponse;
import com.microservices.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public long addproduct(ProductRequest productRequest) {
        log.info("Adding Product");
        //        Product product = Product.builder()
//                .productName(productRequest.getName())
//                .quantity(productRequest.getQuantity())
//                .price(productRequest.getPrice()).
//                build();
        Product product=new Product();
        System.out.println(product.getProductId());
        product.setProductName(productRequest.getName());
        product.setQuantity(productRequest.getQuantity());
        product.setPrice(productRequest.getPrice());

        productRepository.save(product);
        log.info("Product Created"+product.getProductId());
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Get"+"the product Id :"+productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("Product with the given id npt found."));
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product,productResponse);
        return productResponse;
    }
}
