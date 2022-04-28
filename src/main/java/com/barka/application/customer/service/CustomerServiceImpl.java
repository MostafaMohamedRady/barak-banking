package com.barka.application.customer.service;

import com.barka.application.customer.dto.CustomerDto;
import com.barka.application.customer.dto.CustomerRequest;
import com.barka.application.customer.entity.Customer;
import com.barka.application.customer.mapper.CustomerMapper;
import com.barka.application.customer.repository.CustomerRepository;
import com.barka.application.exception.CustomerExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    @Lazy
    private PasswordEncoder bcryptEncoder;

    @Override
    @Transactional
    public CustomerDto signup(CustomerRequest request) {
        log.info("signup for customer [{}]", request.getEmail());
        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomerExistException("There is a customer exist with same email");
        }
        Customer customer = Customer.builder()
                .email(request.getEmail())
                .address(request.getAddress())
                .cif(String.valueOf((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L))
                .eidNo(request.getEidNo())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(bcryptEncoder.encode(request.getPassword()))
                .build();
        customer = customerRepository.save(customer);
        log.info("signup done successfully for customer [{}] with id [{}]", request.getEmail(), customer.getId());
        return CustomerMapper.INSTANCE.entityToResponseDto(customer);
    }

    @Override
    public Customer findByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (!customer.isPresent()) {
            throw new UsernameNotFoundException("Customer not found with email: " + email);
        }
        return customer.get();
    }
}