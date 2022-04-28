package com.barka.application.transaction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternationalTransferRequest {

    @NotNull
    @Schema(name = "fromAccountNo", description = "account number to deduct the mount")
    private Long fromAccountNo;

    @Valid
    private Beneficiary beneficiary;

    @NotNull
    @Positive
    @Schema(name = "amount", description = "amount to be transfer")
    private BigDecimal amount;
}