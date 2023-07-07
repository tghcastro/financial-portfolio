package com.tghcastro.financialportfolio.stocksservice.controller.contracts.request;

import com.tghcastro.financialportfolio.stocksservice.domain.TransactionAction;

import java.time.Instant;

public class PostTransactionBody {
    public String symbol() {
        return symbol;
    }

    public PostTransactionBody setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public Instant executionDate() {
        return executionDate;
    }

    public PostTransactionBody setExecutionDate(Instant executionDate) {
        this.executionDate = executionDate;
        return this;
    }

    public TransactionAction action() {
        return action;
    }

    public PostTransactionBody setAction(TransactionAction action) {
        this.action = action;
        return this;
    }

    public float quantity() {
        return quantity;
    }

    public PostTransactionBody setQuantity(float quantity) {
        this.quantity = quantity;
        return this;
    }

    public float unitPrice() {
        return unitPrice;
    }

    public PostTransactionBody setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public float totalCosts() {
        return totalCosts;
    }

    public PostTransactionBody setTotalCosts(float totalCosts) {
        this.totalCosts = totalCosts;
        return this;
    }

    private String symbol;
    private Instant executionDate;
    private TransactionAction action;
    private float quantity;
    private float unitPrice;
    private float totalCosts;
}
