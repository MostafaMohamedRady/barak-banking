package com.barka.application.transaction.service;

import com.barka.application.account.entity.Account;
import com.barka.application.account.service.AccountService;
import com.barka.application.transaction.dto.DepositRequestDto;
import com.barka.application.transaction.dto.InternationalTransferRequest;
import com.barka.application.transaction.dto.LocalTransferRequest;
import com.barka.application.transaction.dto.TransactionResponse;
import com.barka.application.transaction.dto.TransactionType;
import com.barka.application.transaction.dto.WithdrawRequestDto;
import com.barka.application.transaction.entity.TransactionEntity;
import com.barka.application.transaction.mapper.TransactionMapper;
import com.barka.application.transaction.repository.TransactionRepository;
import com.barka.application.util.AppConstant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Override
    @Transactional
    public TransactionResponse deposit(DepositRequestDto request){
        log.info("money-deposit to account no [{}]", request.getAccountNo());
        Account account = accountService.debitMoney(request);
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .transactionType(TransactionType.DEBIT)
                .account(account)
                .transactionDescription(AppConstant.ATM_DEPOSIT)
                .transactionAmount(request.getAmount())
                .referenceNumber(UUID.randomUUID().toString()).build();
        transactionEntity = transactionRepository.save(transactionEntity);
        log.info("money-deposit done successfully to account [{}]", request.getAccountNo());
        return TransactionMapper.INSTANCE.entityToResponseDto(transactionEntity);
    }

    @Override
    @Transactional
    public TransactionResponse withdraw(WithdrawRequestDto request) {
        log.info("money-withdraw from account no [{}]", request.getAccountNo());
        Account account = accountService.creditMoney(request);
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .transactionType(TransactionType.DEBIT)
                .account(account)
                .transactionDescription(AppConstant.ATM_WITHDRAW)
                .transactionAmount(request.getAmount())
                .referenceNumber(UUID.randomUUID().toString()).build();
        transactionEntity = transactionRepository.save(transactionEntity);
        log.info("money-withdraw done successfully from account [{}]", request.getAccountNo());
        return TransactionMapper.INSTANCE.entityToResponseDto(transactionEntity);
    }


    @Override
    @Transactional
    public TransactionResponse localTransfer(LocalTransferRequest request){
        log.info("local-transfer from account no [{}] to account no [{}]", request.getFromAccountNo(), request.getToAccountNo());
        Account fromAccount = accountService.creditMoney(WithdrawRequestDto.builder()
                .accountNo(request.getFromAccountNo())
                .amount(request.getAmount()).build());
        TransactionEntity fromTransaction = TransactionEntity.builder()
                .transactionType(TransactionType.CREDIT)
                .account(fromAccount)
                .transactionDescription(AppConstant.LOCAL_TRANSFER)
                .transactionAmount(request.getAmount())
                .referenceNumber(UUID.randomUUID().toString()).build();
        fromTransaction = transactionRepository.save(fromTransaction);

        Account toAccount = accountService.debitMoney(DepositRequestDto.builder()
                .accountNo(request.getToAccountNo())
                .amount(request.getAmount()).build());

        TransactionEntity toTransaction = TransactionEntity.builder()
                .transactionType(TransactionType.DEBIT)
                .account(toAccount)
                .transactionDescription(AppConstant.LOCAL_TRANSFER)
                .transactionAmount(request.getAmount())
                .referenceNumber(UUID.randomUUID().toString()).build();
        transactionRepository.save(toTransaction);
        log.info("local-transfer done successfully from account [{}] to account no [{}]", request.getFromAccountNo(), request.getToAccountNo());
        return TransactionMapper.INSTANCE.entityToResponseDto(fromTransaction);
    }

    @Override
    public TransactionResponse internationalTransfer(InternationalTransferRequest request) {
        log.info("international-transfer from account no [{}] to iban no [{}]", request.getFromAccountNo(), request.getBeneficiary());
        //TODO call a third party api to validate iban
        Account fromAccount = accountService.creditMoney(WithdrawRequestDto.builder()
                .accountNo(request.getFromAccountNo())
                .amount(request.getAmount()).build());
        TransactionEntity fromTransaction = TransactionEntity.builder()
                .transactionType(TransactionType.CREDIT)
                .account(fromAccount)
                .transactionDescription(AppConstant.INTERNATIONAL_TRANSFER)
                .transactionAmount(request.getAmount())
                .referenceNumber(UUID.randomUUID().toString()).build();
        fromTransaction = transactionRepository.save(fromTransaction);
        log.info("international-transfer done successfully from account [{}] to iban no [{}]", request.getFromAccountNo(), request.getBeneficiary());
        return TransactionMapper.INSTANCE.entityToResponseDto(fromTransaction);
    }
}