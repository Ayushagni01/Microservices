package com.orderservice.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private long orderId;
    private Instant orderDate;
    private  String orderStatus;
    private long amount;
    private ProductDetails productDetails;
    private PaymentDetails paymentDetails;

    //static inner clas


}


