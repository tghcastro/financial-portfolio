package com.tghcastro.financialportfolio.stocksservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;


@Entity
public class Stock {

    @Id
    @GeneratedValue
    private Long id;
    private String ticker;
    private String company;

    public Stock() {

    }

    public Stock(String ticker) {
        this.setTicker(ticker);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
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
                Objects.equals(this.ticker, stockToCompare.ticker) &&
                Objects.equals(this.company, stockToCompare.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.ticker, this.company);
    }

    @Override
    public String toString() {
        return String.format("Stock [Id:%s] [Ticker:%s] [Company:%s]", this.id, this.ticker, this.company);
    }
}
