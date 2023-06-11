package com.PaymentServicet.PaymentServicet.repository;

import com.PaymentServicet.PaymentServicet.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails,Long> {

    public TransactionDetails findByOrderId(long orderId);

}
