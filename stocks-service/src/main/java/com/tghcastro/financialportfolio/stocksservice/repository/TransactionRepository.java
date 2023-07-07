package com.tghcastro.financialportfolio.stocksservice.repository;

import com.tghcastro.financialportfolio.stocksservice.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountId(Long accountId);
}
