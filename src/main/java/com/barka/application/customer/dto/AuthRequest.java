package com.barka.application.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequest {
    @NotBlank(message = "Email is required")
    @Schema(name = "Email", description = "Email")
    private String email;
    @NotBlank(message = "Password must NOT be empty")
    @Schema(name = "password", description = "User's password")
    private String password;
}
