package com.tghcastro.financialportfolio.stocksservice.service;

import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import com.tghcastro.financialportfolio.stocksservice.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository repository;

    public StockService(StockRepository repository) {
        this.repository = repository;
    }

    public List<Stock> listStocks() {
        return  this.repository.findAll();
    }

    public Stock createStock(Stock newStock) {
        return this.repository.save(newStock);
    }

    public Optional<Stock> findStock(Long id) {
        return this.repository.findById(id);
    }

    public Stock updateStock(Long id, Stock stockToUpdate) {
        return this.repository.findById(id)
                .map(stock -> {
                    stock.setCompany(stockToUpdate.company());
                    stock.setSymbol(stockToUpdate.symbol());
                    return repository.save(stock);
                })
                .orElseGet(() -> {
                    stockToUpdate.setId(id);
                    return repository.save(stockToUpdate);
                });
    }

    public void deactivateStock(Long id) {
        this.repository.deleteById(id);
    }
}
