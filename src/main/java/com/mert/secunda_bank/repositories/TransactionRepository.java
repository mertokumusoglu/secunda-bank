package com.mert.secunda_bank.repositories;

import com.mert.secunda_bank.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccount_AccountNumberOrderByTimestampDesc(Long accountNumber);

}
