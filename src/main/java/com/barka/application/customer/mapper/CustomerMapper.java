package com.barka.application.customer.mapper;

import com.barka.application.customer.dto.CustomerDto;
import com.barka.application.customer.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDto entityToResponseDto(Customer source);

}
