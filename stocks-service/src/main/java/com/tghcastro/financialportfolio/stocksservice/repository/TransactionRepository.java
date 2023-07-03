package com.tghcastro.financialportfolio.stocksservice.repository;

import com.tghcastro.financialportfolio.stocksservice.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    float getStockPosition(Long accountId, String symbol);
}
