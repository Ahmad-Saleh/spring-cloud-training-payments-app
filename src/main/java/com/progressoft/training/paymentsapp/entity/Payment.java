package com.progressoft.training.paymentsapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {

    @Id
    @GeneratedValue
    private Long id;
    private String paymentReference;
    private String payAccountNumber;
    private String bfdAccountNumber;
    private BigDecimal amount;
    private String currencyCode;
    private Date paymentDate;
    private String purpose;

}
