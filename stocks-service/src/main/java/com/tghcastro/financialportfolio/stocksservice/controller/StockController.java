package com.tghcastro.financialportfolio.stocksservice.controller;

import com.tghcastro.financialportfolio.stocksservice.controller.contracts.request.PostStockBody;
import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import com.tghcastro.financialportfolio.stocksservice.exceptions.StockNotFoundException;
import com.tghcastro.financialportfolio.stocksservice.service.StockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/stocks")
    List<Stock> listAll() {
        return stockService.listStocks();
    }

    @PostMapping("/stocks")
    Stock createNew(@RequestBody PostStockBody newStock) {
        Stock stock = new Stock(newStock.symbol());
        stock.setCompany(newStock.company());
        return this.stockService.createStock(stock);
    }

    @GetMapping("/stocks/{id}")
    Stock getOne(@PathVariable Long id) {
        return this.stockService.findStock(id).orElseThrow(() -> new StockNotFoundException(id));
    }

    @PutMapping("/stocks/{id}")
    Stock updateStock(@RequestBody PostStockBody dataToUpdate, @PathVariable Long id) {
        Stock stock = new Stock()
                .setSymbol(dataToUpdate.symbol())
                .setCompany(dataToUpdate.company())
                .setId(id);

        return this.stockService.updateStock(id, stock);
    }

    @DeleteMapping("/stocks/{id}")
    ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        this.stockService.deactivateStock(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
