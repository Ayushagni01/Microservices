package com.orderservice.OrderService.service;

import com.microservices.ProductService.model.ProductResponse;
import com.orderservice.OrderService.entity.Order;
import com.orderservice.OrderService.exception.CustomException;
import com.orderservice.OrderService.external.client.PaymentService;
import com.orderservice.OrderService.external.client.ProductService;
import com.orderservice.OrderService.external.request.PaymentRequest;
import com.orderservice.OrderService.external.response.PaymentResponse;
import com.orderservice.OrderService.model.OrderRequest;
import com.orderservice.OrderService.model.OrderResponse;
import com.orderservice.OrderService.model.PaymentDetails;
import com.orderservice.OrderService.model.ProductDetails;
import com.orderservice.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private RestTemplate restTemplate;

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

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Get Order Details for Order Id {}",orderId);

        Order order=orderRepository.findById(orderId).orElseThrow(
                ()-> new CustomException("Order not found for the Order Id"+orderId,   "NOT_FOUND",404));

        log.info("Invoking product Service to fetch the product service response ");

        ProductResponse productResponse=restTemplate.getForObject("http://PRODUCT-SERVICE/product/"+order.getProductId(),
                ProductResponse.class);

        log.info("Getting Payment information  from the  payment Service");

        PaymentResponse paymentResponse=restTemplate.getForObject("http://PAYMENT-SERVICE/payment/"+order.getId(),PaymentResponse.class);




        ProductDetails productDetails= ProductDetails.builder().
                productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .price(productResponse.getPrice())
                .quantity(productResponse.getQuantity())
                .build();

        PaymentDetails paymentDetails=PaymentDetails.builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentStatus(paymentResponse.getStatus())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();




        OrderResponse orderResponse=new OrderResponse();
        orderResponse.setOrderId(order.getId());
        orderResponse.setOrderStatus(order.getOrderStatus());
        orderResponse.setAmount(order.getAmount());
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setProductDetails(productDetails);
        orderResponse.setPaymentDetails(paymentDetails);
        return orderResponse;
    }

}
