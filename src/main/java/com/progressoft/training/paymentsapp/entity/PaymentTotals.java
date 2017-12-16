package com.progressoft.training.paymentsapp.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@Builder
public class PaymentTotals {

    private String accountNumber;
    private BigDecimal totalAmountPaid;
    private Currency currency;
}
