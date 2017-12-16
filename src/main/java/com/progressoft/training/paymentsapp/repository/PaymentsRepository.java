package com.progressoft.training.paymentsapp.repository;

import com.progressoft.training.paymentsapp.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentsRepository extends JpaRepository<Payment, Long>{

    Payment getPaymentsByPaymentReference(String paymentReference);

    List<Payment> getPaymentsByPayAccountNumber(String payAccountNumber);
}
