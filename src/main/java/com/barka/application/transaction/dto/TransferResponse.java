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
public class TransferResponse {

    @Schema(name = "id", description = "Transaction Reference number")
    private String referenceNumber;

    @Schema(name = "amount", description = "new amount")
    private BigDecimal amount;

    @Schema(name = "from", description = "Transaction source")
    private String from;

    @Schema(name = "to", description = "Transaction account")
    private String to;

    @Schema(name = "to", description = "Transaction type")
    private String transactionType;
}
