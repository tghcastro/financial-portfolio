package unit.service;

import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import com.tghcastro.financialportfolio.stocksservice.domain.Transaction;
import com.tghcastro.financialportfolio.stocksservice.domain.TransactionAction;
import com.tghcastro.financialportfolio.stocksservice.repository.StockRepository;
import com.tghcastro.financialportfolio.stocksservice.repository.TransactionRepository;
import com.tghcastro.financialportfolio.stocksservice.service.TransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static helpers.GenerateTestDataHelper.generateRandomLong;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hibernate.internal.util.collections.CollectionHelper.listOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionServiceTests {

    private TransactionService service;
    private TransactionRepository mockedTransactionRepository;
    private StockRepository mockedStockRepository;

    @BeforeEach
    void beforeEach() {
        mockedTransactionRepository = mock(TransactionRepository.class);
        mockedStockRepository = mock(StockRepository.class);
        service = new TransactionService(mockedTransactionRepository, mockedStockRepository);
    }

    @AfterEach
    void afterEach() {
        Mockito.clearInvocations(mockedTransactionRepository, mockedStockRepository);
    }

    @Test
    void buyStockSuccessfully() {
        Boolean registered = true;
        Transaction transaction = buildSuccessfulTestTransaction(TransactionAction.BUY, registered);

        Transaction registeredTransaction = service.register(transaction);

        assertThat(registeredTransaction).isNotNull();
        assertThat(registeredTransaction.getStock()).isEqualTo(transaction.getStock());
        assertThat(registeredTransaction.getAction()).isEqualTo(transaction.getAction());
        assertThat(registeredTransaction.getQuantity()).isEqualTo(transaction.getQuantity());
        assertThat(registeredTransaction.getUnitPrice()).isEqualTo(transaction.getUnitPrice());
    }

    @Test
    void sellStockSuccessfully() {
        Boolean registered = true;
        Boolean outOfStock = false;
        Transaction transaction = buildSuccessfulTestTransaction(TransactionAction.SELL, registered, outOfStock);

        Transaction registeredTransaction = service.register(transaction);

        assertThat(registeredTransaction).isNotNull();
        assertThat(registeredTransaction.getStock()).isEqualTo(transaction.getStock());
        assertThat(registeredTransaction.getAction()).isEqualTo(transaction.getAction());
        assertThat(registeredTransaction.getQuantity()).isEqualTo(transaction.getQuantity());
        assertThat(registeredTransaction.getUnitPrice()).isEqualTo(transaction.getUnitPrice());
    }

    @Test
    void generateErrorIfStockIsNotAvailable() {
        Stock nonExistentStock = new Stock("NON");
        Transaction transaction = new Transaction(nonExistentStock, TransactionAction.BUY);
        String expectedMessage = String.format("Could not find stock [Symbol: %s]", transaction.getSymbol());

        assertThatThrownBy(() -> service.register(transaction)).hasMessage(expectedMessage);
    }

    @Test
    void generateErrorInCaseOfSellingOutOfStock() {
        Boolean registered = true;
        Boolean outOfStock = true;
        Transaction transaction = buildSuccessfulTestTransaction(TransactionAction.SELL, registered, outOfStock);
        String expectedMessage = String.format("Transaction uses a Stock that is out of stock [Symbol: %s]", transaction.getSymbol());

        assertThatThrownBy(() -> service.register(transaction)).hasMessage(expectedMessage);
    }

    private Transaction buildSuccessfulTestTransaction(TransactionAction action, Boolean registered) {
        Boolean outOfStock = false;
        return buildSuccessfulTestTransaction(action, registered, outOfStock);
    }

    private Transaction buildSuccessfulTestTransaction(TransactionAction action, Boolean registered, Boolean outOfStock) {
        Long account = generateRandomLong();
        String stockSymbol = "ABC";

        Stock stock = new Stock(stockSymbol);

        Transaction transaction = new Transaction(stock, action);
        transaction.setAccountId(account);
        transaction.setQuantity(100);
        transaction.setUnitPrice(10.0F);

        Stock stockToReturn = transaction.getStock();
        List<Transaction> stocksByAccount = listOf(transaction);
        if (!registered) {
            stockToReturn = null;
            stocksByAccount = new ArrayList<>();
        }
        when(mockedStockRepository.findBySymbol(transaction.getSymbol())).thenReturn(stockToReturn);
        when(mockedTransactionRepository.findByAccountId(transaction.getAccountId())).thenReturn(stocksByAccount);

//        float stockPosition = transaction.getQuantity();
//        if (outOfStock) {
//            stockPosition = 0F;
//        }
//        when(mockedTransactionRepository.getStockPosition(account, stockSymbol)).thenReturn(stockPosition);

        return transaction;
    }


}
