package com.tghcastro.financialportfolio.stocksservice.service;

import com.tghcastro.financialportfolio.stocksservice.domain.Transaction;
import com.tghcastro.financialportfolio.stocksservice.exceptions.StockNotFoundException;
import com.tghcastro.financialportfolio.stocksservice.exceptions.TransactionOutOfStockException;
import com.tghcastro.financialportfolio.stocksservice.repository.StockRepository;
import com.tghcastro.financialportfolio.stocksservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final StockRepository stockRepository;

    public TransactionService(TransactionRepository transactionRepository, StockRepository stockRepository) {
        this.transactionRepository = transactionRepository;
        this.stockRepository = stockRepository;
    }

    public Transaction register(Transaction transaction) {

        if (stockRepository.findBySymbol(transaction.getSymbol()) == null) {
            throw new StockNotFoundException(transaction.getSymbol());
        }

        float stockCurrentPosition = transactionRepository.getStockPosition(transaction.getAccountId(), transaction.getSymbol());
        if (stockCurrentPosition <= 0) {
            throw new TransactionOutOfStockException(transaction.getSymbol());
        }

        return transaction;
    }
}
