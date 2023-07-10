package com.tghcastro.financialportfolio.stocksservice.controller.mappers;

import com.tghcastro.financialportfolio.stocksservice.controller.contracts.request.PostStockBody;
import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import com.tghcastro.financialportfolio.stocksservice.dto.StockResponseDto;
import org.modelmapper.ModelMapper;

public class DataConverter {

    static ModelMapper modelMapper = new ModelMapper();

    public static Stock requestToEntity(PostStockBody bodyToConvert, Long id) {
        return new Stock()
                .setId(id)
                .setActive(bodyToConvert.active())
                .setCompany(bodyToConvert.company())
                .setSymbol(bodyToConvert.symbol());
    }

    public static Stock requestToEntity(PostStockBody bodyToConvert) {
        return requestToEntity(bodyToConvert, null);
    }

    public static StockResponseDto entityToResponse(Stock stockEntityToConvert) {
        return new StockResponseDto()
                .setId(stockEntityToConvert.id())
                .setCompany(stockEntityToConvert.company())
                .setActive(stockEntityToConvert.active())
                .setSymbol(stockEntityToConvert.symbol());
    }
}
