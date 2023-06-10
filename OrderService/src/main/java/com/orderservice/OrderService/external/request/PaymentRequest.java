package com.orderservice.OrderService.external.request;

import com.orderservice.OrderService.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PaymentRequest {

    private long orderId;
    private long amount;
    private PaymentMode paymentMode;

    private String referenceNumber;

}
