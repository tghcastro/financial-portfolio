package com.tghcastro.financialportfolio.stocksservice.controller;

import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import com.tghcastro.financialportfolio.stocksservice.exceptions.StockNotFoundException;
import com.tghcastro.financialportfolio.stocksservice.repository.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StockController {

    private final StockRepository repository;

    public StockController(StockRepository stockRepository) {
        this.repository = stockRepository;
    }

    @GetMapping("/stocks")
    List<Stock> listAll() {
        return repository.findAll();
    }

    @PostMapping("/stocks")
    Stock createNew(@RequestBody Stock newEmployee) {
        return repository.save(newEmployee);
    }

    @GetMapping("/stocks/{id}")
    Stock getOne(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new StockNotFoundException(id));
    }

    @PutMapping("/stocks/{id}")
    Stock updateStock(@RequestBody Stock newStock, @PathVariable Long id) {

        return repository.findById(id)
                .map(stock -> {
                    stock.setCompany(newStock.getCompany());
                    stock.setTicker(newStock.getTicker());
                    return repository.save(stock);
                })
                .orElseGet(() -> {
                    newStock.setId(id);
                    return repository.save(newStock);
                });
    }

    @DeleteMapping("/stocks/{id}")
    ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
