package com.tghcastro.financialportfolio.stocksservice.domain;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
public class Stock {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String symbol;

    private String company;

    public Stock() {

    }

    public Stock(String symbol) {
        this.setSymbol(symbol);
    }

    public Long id() {
        return id;
    }

    public Stock setId(Long id) {
        this.id = id;
        return this;
    }

    public String symbol() {
        return symbol;
    }

    public Stock setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String company() {
        return company;
    }

    public Stock setCompany(String company) {
        this.company = company;
        return this;
    }

    @Override
    public boolean equals(Object objectToCompare) {
        if (this == objectToCompare) {
            return true;
        }
        if (!(objectToCompare instanceof Stock stockToCompare)) {
            return false;
        }

        return Objects.equals(this.id, stockToCompare.id) &&
                Objects.equals(this.symbol, stockToCompare.symbol) &&
                Objects.equals(this.company, stockToCompare.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.symbol, this.company);
    }

    @Override
    public String toString() {
        return String.format("Stock [Id:%s] [Symbol:%s] [Company:%s]", this.id, this.symbol, this.company);
    }
}
