package com.barka.application.customer.dto;

import com.barka.application.account.dto.AccountResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    private Long id;
    private String cif;
    private String firstName;
    private String lastName;
    private String eidNo;
    private String address;
    private String email;
    private List<AccountResponseDto> accounts;
}
