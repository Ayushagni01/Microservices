package com.orderservice.OrderService.external.response;

import com.orderservice.OrderService.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long paymentId;
    private String status;
    private PaymentMode paymentMode;
    private  long amount;
    private Instant paymentDate;
    private long orderId;

}
