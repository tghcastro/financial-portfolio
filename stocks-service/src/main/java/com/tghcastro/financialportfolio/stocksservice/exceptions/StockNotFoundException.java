package com.tghcastro.financialportfolio.stocksservice.exceptions;

public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException(Long id) {
        super("Could not find Stock " + id);
    }
}