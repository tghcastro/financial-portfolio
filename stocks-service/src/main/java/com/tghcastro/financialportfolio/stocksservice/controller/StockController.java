package com.tghcastro.financialportfolio.stocksservice.controller;

import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import com.tghcastro.financialportfolio.stocksservice.exceptions.StockNotFoundException;
import com.tghcastro.financialportfolio.stocksservice.repository.StockRepository;
import com.tghcastro.financialportfolio.stocksservice.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StockController {
    private final StockRepository repository;
    private final StockService stockService;

    public StockController(StockRepository repository, StockService stockService) {
        this.repository = repository;
        this.stockService = stockService;
    }

    @GetMapping("/stocks")
    List<Stock> listAll() {
        return stockService.listStocks();
    }

    @PostMapping("/stocks")
    Stock createNew(@RequestBody Stock newStock) {
        return this.stockService.createStock(newStock);
    }

    @GetMapping("/stocks/{id}")
    Stock getOne(@PathVariable Long id) {
        return this.stockService.findStock(id).orElseThrow(() -> new StockNotFoundException(id));
    }

    @PutMapping("/stocks/{id}")
    Stock updateStock(@RequestBody Stock stock, @PathVariable Long id) {
        return this.stockService.updateStock(id, stock);
    }

    @DeleteMapping("/stocks/{id}")
    ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        this.stockService.deactivateStock(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
