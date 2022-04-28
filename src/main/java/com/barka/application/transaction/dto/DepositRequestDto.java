package com.barka.application.transaction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositRequestDto {

    @NotNull
    @Schema(name = "accountNo", description = "account number")
    private Long accountNo;

    @NotNull
    @Positive
    @Schema(name = "amount", description = "amount to deposit")
    private BigDecimal amount;
}
