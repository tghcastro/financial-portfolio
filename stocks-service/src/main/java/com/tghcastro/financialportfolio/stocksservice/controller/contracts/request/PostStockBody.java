package com.tghcastro.financialportfolio.stocksservice.controller.contracts.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PostStockBody {
    public String symbol() {
        return symbol;
    }

    public PostStockBody setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String company() {
        return company;
    }

    public PostStockBody setCompany(String company) {
        this.company = company;
        return this;
    }

    public boolean active() {
        return active;
    }

    public PostStockBody setActive(boolean active) {
        this.active = active;
        return this;
    }

    private String symbol;
    private String company;
    private boolean active;
}
