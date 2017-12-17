package com.progressoft.training.paymentsapp;

import com.progressoft.training.paymentsapp.entity.Payment;
import com.progressoft.training.paymentsapp.repository.PaymentsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class PaymentsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentsAppApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(PaymentsRepository repository) {
        return args -> {
            repository.save(Payment.builder()
                    .paymentReference("PAY-001")
                    .payAccountNumber("000123")
                    .bfdAccountNumber("111111")
                    .amount(new BigDecimal(150))
                    .currencyCode("JOD")
                    .paymentDate(new Date())
                    .purpose("general").build());

            repository.save(Payment.builder()
                    .paymentReference("PAY-002")
                    .payAccountNumber("000123")
                    .bfdAccountNumber("222222")
                    .amount(new BigDecimal(300))
                    .currencyCode("JOD")
                    .paymentDate(new Date())
                    .purpose("general").build());

            repository.save(Payment.builder()
                    .paymentReference("PAY-003")
                    .payAccountNumber("000123")
                    .bfdAccountNumber("555555")
                    .amount(new BigDecimal(15000))
                    .currencyCode("OMR")
                    .paymentDate(new Date())
                    .purpose("donation").build());

            repository.save(Payment.builder()
                    .paymentReference("PAY-004")
                    .payAccountNumber("111111")
                    .bfdAccountNumber("000123")
                    .amount(new BigDecimal(150))
                    .currencyCode("JOD")
                    .paymentDate(new Date())
                    .purpose("general").build());

            repository.save(Payment.builder()
                    .paymentReference("PAY-005")
                    .payAccountNumber("000111")
                    .bfdAccountNumber("151515")
                    .amount(new BigDecimal(36500))
                    .currencyCode("USD")
                    .paymentDate(new Date())
                    .purpose("general").build());
        };
    }

    @Bean
    CommandLineRunner printPayments(PaymentsRepository repository){
        return args -> repository.findAll().forEach(System.out::println);
    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
