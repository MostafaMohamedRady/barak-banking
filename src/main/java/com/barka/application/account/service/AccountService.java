package com.barka.application.account.service;

import com.barka.application.account.dto.AccountRequestDto;
import com.barka.application.account.dto.AccountResponseDto;
import com.barka.application.account.entity.Account;
import com.barka.application.transaction.dto.DepositRequestDto;
import com.barka.application.transaction.dto.LocalTransferRequest;
import com.barka.application.transaction.dto.WithdrawRequestDto;

import java.util.List;

public interface AccountService {

    AccountResponseDto createAccount(AccountRequestDto account);

    AccountResponseDto closeAccount(Long accountNo);

    List<AccountResponseDto> getAccountList();

    Account debitMoney(DepositRequestDto request);

    Account creditMoney(WithdrawRequestDto request);

    Account findActiveAccountByAccountNo(Long accountNo);
}