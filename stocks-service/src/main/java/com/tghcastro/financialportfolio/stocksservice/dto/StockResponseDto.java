package com.tghcastro.financialportfolio.stocksservice.dto;

public class StockResponseDto {
    public Long id;

    public Long id() {
        return id;
    }

    public StockResponseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String symbol() {
        return symbol;
    }

    public StockResponseDto setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String company() {
        return company;
    }

    public StockResponseDto setCompany(String company) {
        this.company = company;
        return this;
    }

    public boolean active() {
        return active;
    }

    public StockResponseDto setActive(boolean active) {
        this.active = active;
        return this;
    }

    public String symbol;
    public String company;
    public boolean active;
}
