package com.barka.application.transaction.mapper;

import com.barka.application.transaction.dto.TransactionResponse;
import com.barka.application.transaction.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionResponse entityToResponseDto(TransactionEntity source);

}
