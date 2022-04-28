package com.barka.application.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO for account")
public class AccountResponseDto {

    @Schema(name = "accountNo", description = "Account Number")
    private BigDecimal accountNo;

    @Schema(name = "iban", description = "iban")
    private String iban;

    @Schema(name = "availableBalance", description = "Account money amount in account currency")
    private BigDecimal availableBalance;

    @Schema(name = "customerId", description = "Account owner ID")
    private Long customerId;

    @Schema(name = "currency", description = "Account currency")
    private Currency currency;

    @Schema(name = "accountType", description = "Account Type")
    private AccountType accountType;

    @Schema(name = "status", description = "Account Status")
    private AccountStatus status;
}