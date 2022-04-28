package com.barka.application.transaction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocalTransferRequest {

    @NotNull
    @Schema(name = "fromAccountNo", description = "account number to deduct the mount")
    private Long fromAccountNo;

    @NotNull
    @Schema(name = "toAccountNo", description = "account number to topup")
    private Long toAccountNo;

    @NotNull
    @Positive
    @Schema(name = "amount", description = "amount to be transfer")
    private BigDecimal amount;

}