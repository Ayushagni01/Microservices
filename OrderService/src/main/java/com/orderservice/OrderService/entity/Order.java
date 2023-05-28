package com.orderservice.OrderService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="ORDER_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderid;
    @Column(name="PRODUCT_ID")
    private long productId;
    @Column(name="QUANTITY")
    private long quantity;
    @Column(name="ORDER_DATE")
    private long orderDate;
    @Column(name="STATUS")
    private String orderStatus;
    @Column(name="TOTAL_AMOUNT")
    private long amount;

}
