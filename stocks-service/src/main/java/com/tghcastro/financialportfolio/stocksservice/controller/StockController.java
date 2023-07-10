package com.tghcastro.financialportfolio.stocksservice.controller;

import com.tghcastro.financialportfolio.stocksservice.controller.contracts.request.PostStockBody;
import com.tghcastro.financialportfolio.stocksservice.dto.StockResponseDto;
import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import com.tghcastro.financialportfolio.stocksservice.service.StockService;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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

    private ModelMapper mapper;

    public  StockController() {
        mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @GetMapping("/stocks")
    ResponseEntity<List<StockResponseDto>> listAll() {
        List<Stock> optionalStock = this.stockService.listStocks();
        List<StockResponseDto> stocks = this.stockService.listStocks()
                .stream()
                .map(s -> toResponseDto(s))
                .toList();
        return ResponseEntity.ok(stocks);
    }

    @PostMapping("/stocks")
    ResponseEntity<StockResponseDto> createNew(@RequestBody PostStockBody newStock) {
        Stock stock = this.stockService.createStock(requestToEntity(newStock));
        return ResponseEntity.ok(toResponseDto(stock));
    }

    @GetMapping("/stocks/{id}")
    ResponseEntity<StockResponseDto> getOne(@PathVariable Long id) {
        Optional<Stock> optionalStock = this.stockService.findStock(id);
        if (optionalStock.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(toResponseDto(optionalStock.get()));
    }

    @PutMapping("/stocks/{id}")
    ResponseEntity<StockResponseDto> updateStock(@RequestBody PostStockBody dataToUpdate, @PathVariable Long id) {
        Optional<Stock> optionalStock = this.stockService.updateStock(requestToEntity(dataToUpdate, id));
        if (optionalStock.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(toResponseDto(optionalStock.get()));
    }

    @DeleteMapping("/stocks/{id}")
    ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        Optional<Stock> optionalStock = this.stockService.deactivateStock(id);
        if (optionalStock.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private StockResponseDto toResponseDto(Stock stock) {
        //return mapper.map(stock, StockResponseDto.class); // TODO: Not working. Why?
        return entityToResponse(stock);
    }
}
