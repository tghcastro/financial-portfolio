package com.tghcastro.financialportfolio.stocksservice.exceptions;

public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException(Long id) {
        super(String.format("Could not find stock [Id: %s]", id));
    }

    public StockNotFoundException(String symbol) {
        super(String.format("Could not find stock [Symbol: %s]", symbol));
    }
}