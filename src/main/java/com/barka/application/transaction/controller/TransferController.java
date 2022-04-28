package com.barka.application.transaction.controller;

import com.barka.application.transaction.dto.InternationalTransferRequest;
import com.barka.application.transaction.dto.LocalTransferRequest;
import com.barka.application.transaction.dto.TransactionResponse;
import com.barka.application.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("internal/v1/transfer")
@AllArgsConstructor
@Tag(name = "Transfer", description = "endpoints to manage the money transfer local/international")
public class TransferController {

    TransactionService transactionService;

    @ApiResponse(responseCode = "200", description = "Local Transfer", content = @Content(
            schema = @Schema(implementation = TransactionResponse.class)
    ))
    @Operation(summary = "Local Transfer",
            description = "<a href='' target='_blank'></a>")
    @ApiResponse(responseCode = "400", description = "Fields to not match validations")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - Not ")
    @ApiResponse(responseCode = "500", description = "Generic internal server error")
    @ApiResponse(responseCode = "502", description = "Server gateway error")
    @PostMapping("/local")
    public ResponseEntity<TransactionResponse> transfer(@RequestBody LocalTransferRequest request){
        return ResponseEntity.ok(transactionService.localTransfer(request));
    }

    @ApiResponse(responseCode = "200", description = "International Transfer", content = @Content(
            schema = @Schema(implementation = TransactionResponse.class)
    ))
    @Operation(summary = "International Transfer",
            description = "<a href='' target='_blank'></a>")
    @ApiResponse(responseCode = "400", description = "Fields to not match validations")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - Not ")
    @ApiResponse(responseCode = "500", description = "Generic internal server error")
    @ApiResponse(responseCode = "502", description = "Server gateway error")
    @PostMapping("/international")
    public ResponseEntity<TransactionResponse> internationalTransfer(@RequestBody InternationalTransferRequest request){
        return ResponseEntity.ok(transactionService.internationalTransfer(request));
    }
}