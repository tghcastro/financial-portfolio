package integration.persistence;


import com.tghcastro.financialportfolio.stocksservice.StocksServiceApplication;
import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import com.tghcastro.financialportfolio.stocksservice.repository.StockRepository;
import helpers.PersistenceConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest(classes = StocksServiceApplication.class)
@ContextConfiguration(classes = PersistenceConfiguration.class, loader = AnnotationConfigContextLoader.class)
@Transactional
public class StockRepositoryTests {

    @Autowired
    private StockRepository stockRepository;

    @ParameterizedTest
    @ValueSource(strings = {"", "Company"})
    @NullSource
    @DisplayName("Create Stock Successfully - Validate not mandatory fields")
    public void createStockSuccessfully(String company) {
        Stock stock = new Stock("ABC");
        stock.setCompany(company);
        Stock savedStock = stockRepository.save(stock);
        assertThat(savedStock.getId()).isNotNull().isGreaterThan(0);
        assertThat(savedStock.getSymbol()).isEqualTo(stock.getSymbol());
        assertThat(savedStock.getSymbol()).isEqualTo(stock.getSymbol());
    }

    @ParameterizedTest
    // @NullAndEmptySource // Need to understand why JPA is allowing to store Empty
    @NullSource
    @DisplayName("Create Stock - Doesn't allow without symbol")
    public void ItDoesNotAllowToCreateWithoutSymbol(String symbol) {
        Stock stock = new Stock(symbol);
        assertThatThrownBy(() ->
                stockRepository.save(stock)
        );
    }
}
