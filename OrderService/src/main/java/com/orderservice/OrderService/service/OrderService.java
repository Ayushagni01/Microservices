package com.orderservice.OrderService.service;

import com.orderservice.OrderService.model.OrderRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
