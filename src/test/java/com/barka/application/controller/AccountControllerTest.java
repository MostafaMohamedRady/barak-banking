package com.barka.application.controller;

import com.barka.application.account.dto.AccountRequestDto;
import com.barka.application.account.dto.AccountResponseDto;
import com.barka.application.account.dto.AccountStatus;
import com.barka.application.account.dto.AccountType;
import com.barka.application.account.dto.Currency;
import com.barka.application.account.entity.Account;
import com.barka.application.account.repository.AccountRepository;
import com.barka.application.customer.entity.Customer;
import com.barka.application.customer.repository.CustomerRepository;
import com.barka.application.customer.service.UserDetailsServiceImpl;
import com.barka.application.secuirty.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest()
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    private String jwtToken;
    private Customer customer;

    @Test
    public void test_create_account() throws Exception {

        UserDetails dummy = new User("createAccount@email.com", "createAccount", new ArrayList<>());
        jwtToken = jwtUtil.generateToken(dummy);

        Mockito.when(userDetailsService.loadUserByUsername(Mockito.eq("createAccount@email.com"))).thenReturn(dummy);

        customer = customerRepository.save(Customer.builder()
                .email("createAccount@email.com")
                .build());

        AccountRequestDto build = AccountRequestDto.builder()
                .accountType(AccountType.CURRENT)
                .currency(Currency.AED)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(build);

        mockMvc.perform(post("/internal/v1/account/")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    public void test_create_account_unAuth() throws Exception {
        AccountRequestDto build = AccountRequestDto.builder()
                .accountType(AccountType.CURRENT)
                .currency(Currency.AED)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(build);

        mockMvc.perform(post("/internal/v1/account/")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void test_close_account() throws Exception {

        UserDetails dummy = new User("closeAccount@email.com", "closeAccount", new ArrayList<>());
        jwtToken = jwtUtil.generateToken(dummy);

        Mockito.when(userDetailsService.loadUserByUsername(Mockito.eq("closeAccount@email.com"))).thenReturn(dummy);

        customer = customerRepository.save(Customer.builder()
                .email("closeAccount@email.com")
                .build());

        Account build = Account.builder()
                .accountType(AccountType.CURRENT)
                .status(AccountStatus.ACTIVE)
                .customer(customer).build();
        Account save = accountRepository.save(build);

        MvcResult mvcResult = mockMvc.perform(delete("/internal/v1/account/account")
                        .param("accountNo", save.getAccountNo().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        AccountResponseDto data = objectMapper.readValue(content, AccountResponseDto.class);
        Assert.assertNotNull(data);
        Assert.assertEquals(AccountStatus.CLOSED, data.getStatus());
    }

}