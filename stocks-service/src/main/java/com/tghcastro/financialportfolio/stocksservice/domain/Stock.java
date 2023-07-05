package com.tghcastro.financialportfolio.stocksservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
