package com.tghcastro.financialportfolio.stocksservice.repository;

import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findBySymbol(String symbol);
}
