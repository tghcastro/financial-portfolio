package com.tghcastro.financialportfolio.stocksservice.service;

import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import com.tghcastro.financialportfolio.stocksservice.domain.Transaction;
import com.tghcastro.financialportfolio.stocksservice.domain.TransactionAction;
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

    public Transaction register(Transaction transaction, String stockSymbol) {

        Stock storedStock = stockRepository.findBySymbol(stockSymbol).orElseThrow(() -> new StockNotFoundException(transaction.getSymbol()));
        transaction.setStock(storedStock);

        if(transaction.getAction() == TransactionAction.SELL) {
            validateSellTransaction(transaction, stockSymbol);
        }

        transactionRepository.save(transaction);

        return transaction;
    }

    public List<Transaction> listTransactions(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    private void validateSellTransaction(Transaction transaction, String stockSymbol) {
        float stockCurrentPosition = loadCurrentStockPosition(transaction.getAccountId(), stockSymbol);
        float futureStockPosition = stockCurrentPosition - transaction.getQuantity();
        if (futureStockPosition < 0) {
            throw new TransactionOutOfStockException(transaction.getSymbol());
        }
    }

    private float loadCurrentStockPosition(Long accountId, String symbol) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);

        double boughtTransactions = transactions.stream()
                .filter(t -> t.getSymbol().equals(symbol) && t.getAction().equals(TransactionAction.BUY))
                .mapToDouble(t -> t.getQuantity()).sum();

        double soldTransactions = transactions.stream()
                .filter(t -> t.getSymbol().equals(symbol) && t.getAction().equals(TransactionAction.SELL))
                .mapToDouble(t -> t.getQuantity()).sum();

        double position = boughtTransactions - soldTransactions;

        return (float)position;
    }



}
