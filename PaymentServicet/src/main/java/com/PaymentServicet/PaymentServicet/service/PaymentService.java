package com.PaymentServicet.PaymentServicet.service;

import com.PaymentServicet.PaymentServicet.model.PaymentRequest;
import com.PaymentServicet.PaymentServicet.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(long orderId);
}
