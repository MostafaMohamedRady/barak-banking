package com.barka.application.transaction.service;

import com.barka.application.transaction.dto.DepositRequestDto;
import com.barka.application.transaction.dto.InternationalTransferRequest;
import com.barka.application.transaction.dto.LocalTransferRequest;
import com.barka.application.transaction.dto.TransactionResponse;
import com.barka.application.transaction.dto.WithdrawRequestDto;

public interface TransactionService {
    TransactionResponse deposit(DepositRequestDto request);

    TransactionResponse withdraw(WithdrawRequestDto request);

    TransactionResponse localTransfer(LocalTransferRequest request);

    TransactionResponse internationalTransfer(InternationalTransferRequest request);
}