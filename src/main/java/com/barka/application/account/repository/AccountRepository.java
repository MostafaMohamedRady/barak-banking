package com.barka.application.account.repository;

import com.barka.application.account.entity.Account;
import com.barka.application.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    @Query("select a from Account a where a.customer = :customer")
    List<Account> findAllByCustomer(@Param("customer") Customer customer);

    @Query("select a from Account a where a.accountNo = :accountNo")
    Optional<Account> findAccountByAccountNo(@Param("accountNo") Long accountNo);
}