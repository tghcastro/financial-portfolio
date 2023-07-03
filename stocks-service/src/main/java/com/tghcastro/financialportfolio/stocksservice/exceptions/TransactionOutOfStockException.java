package com.tghcastro.financialportfolio.stocksservice.exceptions;

public class TransactionOutOfStockException extends RuntimeException {
    public TransactionOutOfStockException(String symbol) {
        super(String.format("Transaction uses a Stock that is out of stock [Symbol: %s]", symbol));
    }
}
