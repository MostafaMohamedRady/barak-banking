package com.barka.application.transaction.controller;

import com.barka.application.transaction.dto.DepositRequestDto;
import com.barka.application.transaction.dto.TransactionResponse;
import com.barka.application.transaction.dto.WithdrawRequestDto;
import com.barka.application.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("webhook/v1/money")
@AllArgsConstructor
@Tag(name = "ATM Transactions", description = "endpoints to manage the atm transactions deposit/withdrawal")
public class ATMTransactionController {

    private final TransactionService transactionService;

    @ApiResponse(responseCode = "200", description = "Money Deposit", content = @Content(
            schema = @Schema(implementation = TransactionResponse.class)
    ))
    @Operation(summary = "Money Deposit",
            description = "<a href='' target='_blank'></a>")
    @ApiResponse(responseCode = "400", description = "Fields to not match validations")
    @ApiResponse(responseCode = "403", description = "Forbidden - Not ")
    @ApiResponse(responseCode = "500", description = "Generic internal server error")
    @ApiResponse(responseCode = "502", description = "Server gateway error")
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@RequestBody DepositRequestDto request) {
        return ResponseEntity.ok(transactionService.deposit(request));
    }

    @ApiResponse(responseCode = "200", description = "Money Withdraw", content = @Content(
            schema = @Schema(implementation = TransactionResponse.class)
    ))
    @Operation(summary = "Money Withdraw",
            description = "<a href='' target='_blank'></a>")
    @ApiResponse(responseCode = "400", description = "Fields to not match validations")
    @ApiResponse(responseCode = "403", description = "Forbidden - Not ")
    @ApiResponse(responseCode = "500", description = "Generic internal server error")
    @ApiResponse(responseCode = "502", description = "Server gateway error")
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@RequestBody WithdrawRequestDto request){
        return ResponseEntity.ok(transactionService.withdraw(request));
    }

}