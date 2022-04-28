package com.barka.application.customer.service;

import com.barka.application.customer.dto.CustomerDto;
import com.barka.application.customer.dto.CustomerRequest;
import com.barka.application.customer.entity.Customer;

public interface CustomerService {
    CustomerDto signup(CustomerRequest request);

    Customer findByEmail(String name);
}