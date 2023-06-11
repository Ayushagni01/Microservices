package com.PaymentServicet.PaymentServicet.service;

import com.PaymentServicet.PaymentServicet.entity.TransactionDetails;
import com.PaymentServicet.PaymentServicet.model.PaymentMode;
import com.PaymentServicet.PaymentServicet.model.PaymentRequest;
import com.PaymentServicet.PaymentServicet.model.PaymentResponse;
import com.PaymentServicet.PaymentServicet.repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private TransactionDetailsRepository   transactionDetailsRepository;
    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Regarding Payment Details {}",paymentRequest);
        TransactionDetails transactionDetails=new TransactionDetails();
        transactionDetails.setPaymentDate(Instant.now());
                transactionDetails.setPaymentMode(paymentRequest.getPaymentMode().name());
        transactionDetails.setPayemntStatus("SUCCESS");
        transactionDetails.setOrderId(paymentRequest.getOrderId());
        transactionDetails.setReferenceNumber(paymentRequest.getReferenceNumber());
        transactionDetails.setAmount(paymentRequest.getAmount());

        transactionDetailsRepository.save(transactionDetails);
        log.info("Transaction Completed With id {}",transactionDetails.getId());

        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(long orderId) {
        log.info("Getting Payment Details for the Order id");
        TransactionDetails details=transactionDetailsRepository.findByOrderId(orderId);

        PaymentResponse response= PaymentResponse.builder()
                .paymentId(details.getId())
                .paymentMode(PaymentMode.valueOf(details.getPaymentMode())).
                paymentDate(details.getPaymentDate())
                .orderId(details.getOrderId())
                .status(details.getPayemntStatus())
                .amount(details.getAmount())
                .build();
        return  response;
    }
}
