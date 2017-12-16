package com.progressoft.training.paymentsapp.controller;

import com.progressoft.training.paymentsapp.entity.Payment;
import com.progressoft.training.paymentsapp.repository.PaymentsRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentsController {

    private PaymentsRepository repository;

    public PaymentsController(PaymentsRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/payments/byRef")
    public Payment getPaymentByReference(@RequestParam(name = "ref") String paymentReference){
        return repository.getPaymentsByPaymentReference(paymentReference);
    }

    @RequestMapping("/payments/byPayAccount")
    public List<Payment> getPaymentsByPayAccountNumber(@RequestParam(name = "payAccount") String payAccountNumber){
        return repository.getPaymentsByPayAccountNumber(payAccountNumber);
    }
}
