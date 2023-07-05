package integration.persistence;


import com.tghcastro.financialportfolio.stocksservice.StocksServiceApplication;
import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import com.tghcastro.financialportfolio.stocksservice.repository.StockRepository;
import helpers.PersistenceConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = StocksServiceApplication.class)
@ContextConfiguration(classes = PersistenceConfiguration.class, loader = AnnotationConfigContextLoader.class)
@Transactional
public class StockRepositoryTests {

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void stockRepositoryBasicOperationsTest() {
        Stock stock = new Stock("ABC");
        stock.setCompany("ABC COMPANY");
        Stock savedStock = stockRepository.save(stock);

        assertThat(savedStock.getId()).isNotNull().isGreaterThan(0);
        assertThat(savedStock.getSymbol()).isEqualTo(stock.getSymbol());
        assertThat(savedStock.getSymbol()).isEqualTo(stock.getSymbol());
    }

}
