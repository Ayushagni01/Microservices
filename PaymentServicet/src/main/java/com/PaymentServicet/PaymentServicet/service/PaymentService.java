package com.PaymentServicet.PaymentServicet.service;

import com.PaymentServicet.PaymentServicet.model.PaymentRequest;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);
}
