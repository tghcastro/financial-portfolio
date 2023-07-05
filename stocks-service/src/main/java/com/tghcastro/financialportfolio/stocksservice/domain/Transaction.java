package com.tghcastro.financialportfolio.stocksservice.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;
    private Long accountId;

    @ManyToOne(targetEntity = Stock.class)
    private Stock stock;
    private Date executionDate;
    private Date registerDate;
    private TransactionAction action;
    private float quantity;
    private float unitPrice;
    private float localValue;
    private float accountValue;
    private float exchangeRate;
    private float totalCosts;

    public Transaction(Stock stock, TransactionAction action) {
        this.stock = stock;
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Stock getStock() {
        return stock;
    }

    public String getSymbol() {
        return stock.getSymbol();
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public Date getRegisterDateDate() {
        return registerDate;
    }

    public void setRegisterDateDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public TransactionAction getAction() {
        return action;
    }

    public void setAction(TransactionAction action) {
        this.action = action;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getLocalValue() {
        return localValue;
    }

    public void setLocalValue(float localValue) {
        this.localValue = localValue;
    }

    public float getAccountValue() {
        return accountValue;
    }

    public void setAccountValue(float accountValue) {
        this.accountValue = accountValue;
    }

    public float getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(float exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public float getTotalCosts() {
        return totalCosts;
    }

    public void setTotalCosts(float totalCosts) {
        this.totalCosts = totalCosts;
    }

}
