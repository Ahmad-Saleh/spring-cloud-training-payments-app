package com.progressoft.training.paymentsapp.controller;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.progressoft.training.paymentsapp.entity.Payment;
import com.progressoft.training.paymentsapp.entity.PaymentTotals;
import com.progressoft.training.paymentsapp.repository.PaymentsRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

@RestController
public class PaymentTotalsController {

    private static Table<Currency, Currency, BigDecimal> exchangesTable;

    static {
        exchangesTable = HashBasedTable.create();
        exchangesTable.put(Currency.getInstance("JOD"), Currency.getInstance("USD"), new BigDecimal(1.4));
        exchangesTable.put(Currency.getInstance("JOD"), Currency.getInstance("OMR"), new BigDecimal(.5));
        exchangesTable.put(Currency.getInstance("USD"), Currency.getInstance("JOD"), new BigDecimal(0.7));
        exchangesTable.put(Currency.getInstance("USD"), Currency.getInstance("OMR"), new BigDecimal(0.5 * 0.7));
        exchangesTable.put(Currency.getInstance("OMR"), Currency.getInstance("JOD"), new BigDecimal(2));
        exchangesTable.put(Currency.getInstance("OMR"), Currency.getInstance("USD"), new BigDecimal(1 / (0.5 * 0.7)));
    }

    private PaymentsRepository repository;

    public PaymentTotalsController(PaymentsRepository repository) {
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
        if (fromCurrencyCode.equals(toCurrencyCode)) {
            return BigDecimal.ONE;
        }
        return exchangesTable.get(Currency.getInstance(fromCurrencyCode), Currency.getInstance(toCurrencyCode));
    }
}
