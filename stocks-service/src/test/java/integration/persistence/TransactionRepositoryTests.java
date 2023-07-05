package integration.persistence;

import com.tghcastro.financialportfolio.stocksservice.StocksServiceApplication;
import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import com.tghcastro.financialportfolio.stocksservice.domain.Transaction;
import com.tghcastro.financialportfolio.stocksservice.domain.TransactionAction;
import com.tghcastro.financialportfolio.stocksservice.repository.StockRepository;
import com.tghcastro.financialportfolio.stocksservice.repository.TransactionRepository;
import helpers.PersistenceConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import static helpers.GenerateTestDataHelper.generateRandomLong;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = StocksServiceApplication.class)
@ContextConfiguration(classes = PersistenceConfiguration.class, loader = AnnotationConfigContextLoader.class)
@Transactional
public class TransactionRepositoryTests {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private StockRepository stockRepository;


    @Test
    public void createTransactionTest() {
        Long account = generateRandomLong();
        String stockSymbol = "ABC";

        Stock stock = new Stock(stockSymbol);

        Transaction transaction = new Transaction(stock, TransactionAction.BUY);
        transaction.setAccountId(account);
        transaction.setQuantity(100);
        transaction.setUnitPrice(10.0F);

        stockRepository.save(stock);

        Transaction savedTransaction = transactionRepository.save(transaction);

        assertThat(savedTransaction.getId()).isNotNull().isGreaterThan(0);
        assertThat(savedTransaction.getStock()).isEqualTo(stock);
        assertThat(savedTransaction.getSymbol()).isEqualTo(transaction.getSymbol());
    }


}
