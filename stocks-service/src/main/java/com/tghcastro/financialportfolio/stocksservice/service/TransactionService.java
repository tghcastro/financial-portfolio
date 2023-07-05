package com.tghcastro.financialportfolio.stocksservice.service;

import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import com.tghcastro.financialportfolio.stocksservice.domain.Transaction;
import com.tghcastro.financialportfolio.stocksservice.exceptions.StockNotFoundException;
import com.tghcastro.financialportfolio.stocksservice.exceptions.TransactionOutOfStockException;
import com.tghcastro.financialportfolio.stocksservice.repository.StockRepository;
import com.tghcastro.financialportfolio.stocksservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final StockRepository stockRepository;

    public TransactionService(TransactionRepository transactionRepository, StockRepository stockRepository) {
        this.transactionRepository = transactionRepository;
        this.stockRepository = stockRepository;
    }

    public Transaction register(Transaction transaction) {

        Stock storedStock = stockRepository.findById(transaction.getId()).orElseThrow(() -> new StockNotFoundException(transaction.getSymbol()));

        float stockCurrentPosition = loadStockPosition(transaction);
        if (stockCurrentPosition <= 0) {
            throw new TransactionOutOfStockException(transaction.getSymbol());
        }

        return transaction;
    }

    private float loadStockPosition(Transaction transaction) {
        //return transactionRepository.getStockPosition(transaction.getAccountId());
        List<Transaction> transactions = transactionRepository.findByAccountId(transaction.getAccountId());

        double position = transactions.stream().mapToDouble(t -> t.getQuantity()).sum();
        return (float)position;
    }
}
