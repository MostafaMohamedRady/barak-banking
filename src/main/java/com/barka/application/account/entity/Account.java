package com.barka.application.account.entity;

import com.barka.application.account.dto.AccountStatus;
import com.barka.application.account.dto.AccountType;
import com.barka.application.account.dto.Currency;
import com.barka.application.customer.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountNo;

    private String iban;

    @Column(name = "availableBalance")
    private BigDecimal availableBalance;

    private AccountType accountType;

    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;

}