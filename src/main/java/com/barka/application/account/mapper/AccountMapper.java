package com.barka.application.account.mapper;

import com.barka.application.account.dto.AccountResponseDto;
import com.barka.application.account.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountResponseDto entityToResponseDto(Account source);
}
