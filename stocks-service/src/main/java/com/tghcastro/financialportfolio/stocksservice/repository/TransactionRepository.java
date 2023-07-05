package com.tghcastro.financialportfolio.stocksservice.repository;

import com.tghcastro.financialportfolio.stocksservice.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    @Query("SELECT sum(quantity) as stockPosition FROM transaction t WHERE t.accountId = ?1")
//    float getStockPosition(Long accountId);

    List<Transaction> findByAccountId(Long accountId);
}
