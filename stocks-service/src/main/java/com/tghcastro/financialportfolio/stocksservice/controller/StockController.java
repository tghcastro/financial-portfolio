package com.tghcastro.financialportfolio.stocksservice.controller;

import com.tghcastro.financialportfolio.stocksservice.controller.contracts.request.PostStockBody;
import com.tghcastro.financialportfolio.stocksservice.dto.StockResponseDto;
import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import com.tghcastro.financialportfolio.stocksservice.service.StockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.tghcastro.financialportfolio.stocksservice.controller.mappers.DataConverter.*;

@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/stocks")
    ResponseEntity<List<StockResponseDto>> listAll() {
        List<Stock> optionalStock = this.stockService.listStocks();
        List<StockResponseDto> stocks = this.stockService.listStocks()
                .stream()
                .map(s -> entityToResponse(s))
                .toList();
        return ResponseEntity.ok(stocks);
    }

    @PostMapping("/stocks")
    ResponseEntity<StockResponseDto> createNew(@RequestBody PostStockBody newStock) {
        Stock stock = this.stockService.createStock(requestToEntity(newStock));
        return ResponseEntity.ok(entityToResponse(stock));
    }

    @GetMapping("/stocks/{id}")
    ResponseEntity<StockResponseDto> getOne(@PathVariable Long id) {
        Optional<Stock> optionalStock = this.stockService.findStock(id);
        if (optionalStock.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(entityToResponse(optionalStock.get()));
    }

    @PutMapping("/stocks/{id}")
    ResponseEntity<StockResponseDto> updateStock(@RequestBody PostStockBody dataToUpdate, @PathVariable Long id) {
        Stock updatedStock = this.stockService.updateStock(requestToEntity(dataToUpdate, id));
        return ResponseEntity.ok(entityToResponse(updatedStock));
    }

    @DeleteMapping("/stocks/{id}")
    ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        this.stockService.deactivateStock(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
