package com.tghcastro.financialportfolio.stocksservice.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class StockTests {

    @Test
    void stockHasCorrectStringDefinitionWhenToString() {
        Stock someStock = new Stock("KO");
        someStock.setId(1L);
        someStock.setCompany("Coca-Cola");
        String expectedStringDefinition = "Stock [Id:1] [Ticker:KO] [Company:Coca-Cola]";

        assertThat(someStock.toString()).isEqualTo(expectedStringDefinition);
    }

    @Test
    void stocksAreEqualEqualWhenTheirFieldsHaveSameValue() {
        Stock stockA = new Stock("ABC");
        Stock stockB = new Stock("ABC");

        stockA.setId(1L);
        stockB.setId(1L);

        stockA.setCompany("ABC Co.");
        stockB.setCompany("ABC Co.");

        assertThat(stockA).isEqualTo(stockB);
    }

    @Test
    void stockIsNotEqualWhenTheirIdsAreDifferent() {
        Stock stockA = new Stock("ABC");
        Stock stockB = new Stock("ABC");

        stockA.setId(1L);
        stockB.setId(2L);

        assertThat(stockA).isNotEqualTo(stockB);
    }

    @Test
    void stockIsNotEqualWhenTheirTickersAreDifferent() {
        Stock stockA = new Stock("DEF");
        Stock stockB = new Stock("ABC");

        stockA.setId(1L);
        stockB.setId(1L);

        assertThat(stockA).isNotEqualTo(stockB);
    }

    @Test
    void stockIsNotEqualWhenTheirCompaniesAreDifferent() {
        Stock stockA = new Stock("ABC");
        Stock stockB = new Stock("ABC");

        stockA.setId(1L);
        stockB.setId(1L);

        stockA.setCompany("ABC Co.");
        stockB.setCompany("DEF Co.");

        assertThat(stockA).isNotEqualTo(stockB);
    }
}
