package com.barka.application.account.service;

import com.barka.application.account.dto.AccountRequestDto;
import com.barka.application.account.dto.AccountResponseDto;
import com.barka.application.account.dto.AccountStatus;
import com.barka.application.account.entity.Account;
import com.barka.application.account.mapper.AccountMapper;
import com.barka.application.account.repository.AccountRepository;
import com.barka.application.customer.entity.Customer;
import com.barka.application.customer.service.CustomerServiceImpl;
import com.barka.application.customer.service.CustomerUtil;
import com.barka.application.exception.AccountNotFoundException;
import com.barka.application.exception.BalanceException;
import com.barka.application.transaction.dto.DepositRequestDto;
import com.barka.application.transaction.dto.WithdrawRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerServiceImpl customerService;
    private final CustomerUtil userUtil;

    @Override
    @Transactional
    public AccountResponseDto createAccount(final AccountRequestDto requestDto) {
        log.info("create-account [{}]", requestDto);
        Customer customer = customerService.findByEmail(userUtil.getPrincipal().getUsername());
        Account account = Account.builder()
                .status(AccountStatus.ACTIVE)
                .availableBalance(BigDecimal.ZERO)
                .currency(requestDto.getCurrency())
                .accountType(requestDto.getAccountType())
                .customer(customer)
                .build();
        account = accountRepository.save(account);
        log.info("create-account - account has been created for user [{}]", customer.getId());
        return AccountMapper.INSTANCE.entityToResponseDto(account);
    }

    @Override
    @Transactional
    public AccountResponseDto closeAccount(Long accountNo) {
        log.info("close-account [{}]", accountNo);
        Customer customer = customerService.findByEmail(userUtil.getPrincipal().getUsername());
        Account account = findActiveAccountByAccountNo(accountNo);
        account.setStatus(AccountStatus.CLOSED);
        account = accountRepository.save(account);
        log.info("close-account - account has been closed [{}]", customer.getId());
        return AccountMapper.INSTANCE.entityToResponseDto(account);
    }

    @Override
    @Transactional
    public List<AccountResponseDto> getAccountList() {
        log.info("account-list");
        Customer customer = customerService.findByEmail(userUtil.getPrincipal().getUsername());
        return accountRepository.findAllByCustomer(customer).stream()
                .map(AccountMapper.INSTANCE::entityToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Account debitMoney(DepositRequestDto request) {
        log.info("money-deposit to account [{}] with amount [{}]", request.getAccountNo(), request.getAmount());
        Account account = findActiveAccountByAccountNo(request.getAccountNo());
        account.setAvailableBalance(account.getAvailableBalance().add(request.getAmount()));
        return accountRepository.save(account);
    }

    @Override
    public Account creditMoney(WithdrawRequestDto request) {
        Account account = findActiveAccountByAccountNo(request.getAccountNo());
        if (account.getAvailableBalance().compareTo(request.getAmount()) == -1) {
            throw new BalanceException("You don't have enough balance");
        }
        account.setAvailableBalance(account.getAvailableBalance().subtract(request.getAmount()));
        return accountRepository.save(account);
    }

    public Account findActiveAccountByAccountNo(Long accountNo) {
        return accountRepository.findAccountByAccountNo(accountNo)
                .orElseThrow(() -> new AccountNotFoundException("Account [ " + accountNo + " ] not found"));
    }
}