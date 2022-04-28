package com.barka.application.customer.controller;


import com.barka.application.customer.dto.AuthRequest;
import com.barka.application.customer.dto.AuthResponse;
import com.barka.application.customer.dto.CustomerDto;
import com.barka.application.customer.dto.CustomerRequest;
import com.barka.application.customer.service.CustomerService;
import com.barka.application.customer.service.UserDetailsServiceImpl;
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

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/customer")
@AllArgsConstructor
@Tag(name = "CUSTOMER", description = "endpoints to manage the customers")
public class CustomerController {

    private final CustomerService customerService;
    private final UserDetailsServiceImpl detailsService;

    @ApiResponse(responseCode = "200", description = "signup customer", content = @Content(
            schema = @Schema(implementation = CustomerDto.class)
    ))
    @Operation(summary = "signup customer",
            description = "<a href='' target='_blank'></a>")
    @ApiResponse(responseCode = "400", description = "Fields to not match validations")
    @ApiResponse(responseCode = "403", description = "Forbidden - Not ")
    @ApiResponse(responseCode = "500", description = "Generic internal server error")
    @ApiResponse(responseCode = "502", description = "Server gateway error")
    @PostMapping("/signup")
    public ResponseEntity<CustomerDto> signup(@Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerService.signup(request));
    }

    @ApiResponse(responseCode = "200", description = "Authorize customer", content = @Content(
            schema = @Schema(implementation = AuthResponse.class)
    ))
    @Operation(summary = "Authorize customer",
            description = "<a href='' target='_blank'></a>")
    @ApiResponse(responseCode = "400", description = "Fields to not match validations")
    @ApiResponse(responseCode = "403", description = "Forbidden - Not ")
    @ApiResponse(responseCode = "500", description = "Generic internal server error")
    @ApiResponse(responseCode = "502", description = "Server gateway error")
    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> authorize(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(detailsService.authorize(request));
    }
}