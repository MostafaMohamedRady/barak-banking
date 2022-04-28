package com.barka.application.transaction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beneficiary {
    @NotNull
    @Schema(name = "iban", description = "iban")
    private String iban;

    @NotNull
    @Schema(name = "bankName", description = "Bank Name")
    private String bankName;
}
