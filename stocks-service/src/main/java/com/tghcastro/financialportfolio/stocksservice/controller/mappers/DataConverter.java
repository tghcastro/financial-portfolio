package com.tghcastro.financialportfolio.stocksservice.controller.mappers;

import com.tghcastro.financialportfolio.stocksservice.controller.contracts.request.PostStockBody;
import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import com.tghcastro.financialportfolio.stocksservice.dto.StockResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;


public class DataConverter {

    static ModelMapper modelMapper = new ModelMapper();

    public static Stock requestToEntity(PostStockBody bodyToConvert, Long id) {
        return new Stock()
                .setId(id)
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
            .setSymbol(stockEntityToConvert.symbol());
    }

    public static List<StockResponseDto> entityListToResponse(List<Stock> objectToConvert) {
        return modelMapper.map(objectToConvert, List.class);
    }
}
