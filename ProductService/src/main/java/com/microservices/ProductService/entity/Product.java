package com.microservices.ProductService.entity;

import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;
    @Column(name="PRODUCT_NAME")
    private String productName;
    @Column(name="PRICE")
    private long price;
    @Column(name="QUANTITY")
    private long quantity;

}
