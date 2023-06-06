package com.orderservice.OrderService.service;

import com.orderservice.OrderService.entity.Order;
import com.orderservice.OrderService.model.OrderRequest;
import com.orderservice.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Placing order request"+orderRequest);
        Order order = new Order();
        order.setAmount(orderRequest.getTotalAmount());
        order.setOrderStatus("CREATED");
        order.setProductId(orderRequest.getProductId());
        //order.setOrderDate(new Date());
        order.setOrderDate(Instant.now());
        order.setQuantity(orderRequest.getQuantity());
        order=orderRepository.save(order);
        log.info("oder placed succssfully with order id"+ order.getId());
        return order.getId() ;

             /*   Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();*/
    }
}
