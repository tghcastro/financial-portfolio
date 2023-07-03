package com.tghcastro.financialportfolio.stocksservice.repository;

import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findBySymbol(String symbol);
}
