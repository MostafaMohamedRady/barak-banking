package com.barka.application.account.controller;

import com.barka.application.account.dto.AccountRequestDto;
import com.barka.application.account.dto.AccountResponseDto;
import com.barka.application.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("internal/v1/account")
@Tag(name = "ACCOUNTS", description = "endpoints to manage the accounts")
@AllArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class AccountController {


    private final AccountService accountService;

    @ApiResponse(responseCode = "200", description = "Create account", content = @Content(
            schema = @Schema(implementation = AccountResponseDto.class)
    ))
    @Operation(summary = "Create account",
            description = "<a href='' target='_blank'></a>")
    @ApiResponse(responseCode = "400", description = "Fields to not match validations")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - Not ")
    @ApiResponse(responseCode = "500", description = "Generic internal server error")
    @ApiResponse(responseCode = "502", description = "Server gateway error")
    @SecurityRequirement(name = "BearerAuth")
    @PostMapping
    public ResponseEntity<AccountResponseDto> create(@RequestBody AccountRequestDto account) {
        return  ResponseEntity.ok(accountService.createAccount(account));
    }

    @ApiResponse(responseCode = "200", description = "Close account", content = @Content(
            schema = @Schema(implementation = AccountResponseDto.class)
    ))
    @Operation(summary = "Close account",
            description = "<a href='' target='_blank'></a>")
    @ApiResponse(responseCode = "400", description = "Fields to not match validations")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - Not ")
    @ApiResponse(responseCode = "500", description = "Generic internal server error")
    @ApiResponse(responseCode = "502", description = "Server gateway error")
    @SecurityRequirement(name = "BearerAuth")
    @DeleteMapping("/account")
    public ResponseEntity<AccountResponseDto> closeAccount(@Parameter(name = "accountNo", description = "account number") @RequestParam Long accountNo){
        return  ResponseEntity.ok(accountService.closeAccount(accountNo));
    }

    @ApiResponse(responseCode = "200", description = "Listing accounts", content = @Content(
            schema = @Schema(implementation = AccountResponseDto.class)
    ))
    @Operation(summary = "Listing accounts",
            description = "<a href='' target='_blank'></a>")
    @ApiResponse(responseCode = "400", description = "Fields to not match validations")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - Not ")
    @ApiResponse(responseCode = "500", description = "Generic internal server error")
    @ApiResponse(responseCode = "502", description = "Server gateway error")
    @SecurityRequirement(name = "BearerAuth")
    @GetMapping("/")
    public ResponseEntity<List<AccountResponseDto>> getAccountList() {
        return  ResponseEntity.ok(accountService.getAccountList());
    }
}