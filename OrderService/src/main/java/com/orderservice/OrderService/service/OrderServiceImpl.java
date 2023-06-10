package com.orderservice.OrderService.service;

import com.orderservice.OrderService.entity.Order;
import com.orderservice.OrderService.external.client.PaymentService;
import com.orderservice.OrderService.external.client.ProductService;
import com.orderservice.OrderService.external.request.PaymentRequest;
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

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        //call another MicroService Api through the Feign Client for this I have created the Inteface name ProductService
        //which call the reduceQuantity method inside the ProductController
        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());
        log.info("Creating Order with Status Created");
        Order order = new Order();
        order.setAmount(orderRequest.getTotalAmount());
        order.setOrderStatus("CREATED");
        order.setProductId(orderRequest.getProductId());
        //order.setOrderDate(new Date());
        order.setOrderDate(Instant.now());
        order.setQuantity(orderRequest.getQuantity());
        log.info("Saving order data into the database");
        order=orderRepository.save(order);

        log.info("Calling Payment Service to complete the payment ");
        PaymentRequest paymentRequest=new PaymentRequest();
        paymentRequest.setOrderId(order.getId());
        paymentRequest.setPaymentMode(orderRequest.getPaymentMode());
        paymentRequest.setAmount(orderRequest.getTotalAmount());

        String orderStatus=null;
        try{
        paymentService.doPayment(paymentRequest);
        log.info("Payment Done Successfully Changing Order Status to PLACED");
        orderStatus="PLACED";
        }catch(Exception e){
        log.error("Error occured in payment");
        orderStatus="PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("oder placed succssfully with order id"+ order.getId());


        return order.getId() ;

    }
}
