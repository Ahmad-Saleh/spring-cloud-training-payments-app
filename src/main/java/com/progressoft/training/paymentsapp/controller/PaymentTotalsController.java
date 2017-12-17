package com.progressoft.training.paymentsapp.controller;

import com.progressoft.training.paymentsapp.entity.Payment;
import com.progressoft.training.paymentsapp.entity.PaymentTotals;
import com.progressoft.training.paymentsapp.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@RestController
public class PaymentTotalsController {

    private RestTemplate restTemplate;
    private PaymentsRepository repository;

    @Value("${exchange-service.url}")
    private String baseUrl;

    public PaymentTotalsController(RestTemplate restTemplate, PaymentsRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    @RequestMapping("/totals")
    public PaymentTotals getTotalsForAccount(@RequestParam String account, @RequestParam String currencyCode) {
        List<Payment> paymentsSent = repository.getPaymentsByPayAccountNumber(account);
        BigDecimal totalAmount = paymentsSent.stream()
                .map(p -> exchangeAmount(p.getAmount(), p.getCurrencyCode(), currencyCode))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return PaymentTotals.builder()
                .accountNumber(account)
                .currency(Currency.getInstance(currencyCode))
                .totalAmountPaid(totalAmount).build();
    }

    private BigDecimal exchangeAmount(BigDecimal amount, String fromCurrencyCode, String toCurrencyCode) {
        return amount.multiply(getExchangeRate(fromCurrencyCode, toCurrencyCode));
    }

    private BigDecimal getExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("from", fromCurrencyCode);
        parametersMap.put("to", toCurrencyCode);
        return restTemplate.getForEntity(baseUrl, BigDecimal.class, parametersMap).getBody();
    }

}
