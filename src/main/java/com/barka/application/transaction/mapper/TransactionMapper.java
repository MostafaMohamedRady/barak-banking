package com.barka.application.transaction.mapper;

import com.barka.application.transaction.dto.TransactionResponse;
import com.barka.application.transaction.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "accountNo", source = "source.account.accountNo")
    TransactionResponse entityToResponseDto(TransactionEntity source);

}
