package com.barka.application.transaction.dto;

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
@Schema(description = "AtmTransactionResponse")
public class TransactionResponse {

    @Schema(name = "id", description = "Transaction Reference number")
    private String referenceNumber;

    @Schema(name = "transactionAmount", description = "new amount")
    private BigDecimal transactionAmount;

    @Schema(name = "accountNo", description = "Transaction accountNo")
    private Long accountNo;

    @Schema(name = "transactionType", description = "Transaction type")
    private String transactionType;

    @Schema(name = "transactionDescription", description = "transactionDescription")
    private String transactionDescription;
}
