package com.PaymentServicet.PaymentServicet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.Instant;


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
