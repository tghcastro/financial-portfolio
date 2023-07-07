package unit.service;

import com.tghcastro.financialportfolio.stocksservice.domain.Stock;
import com.tghcastro.financialportfolio.stocksservice.domain.Transaction;
import com.tghcastro.financialportfolio.stocksservice.domain.TransactionAction;
import com.tghcastro.financialportfolio.stocksservice.repository.StockRepository;
import com.tghcastro.financialportfolio.stocksservice.repository.TransactionRepository;
import com.tghcastro.financialportfolio.stocksservice.service.TransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static helpers.GenerateTestDataHelper.generateRandomUUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hibernate.internal.util.collections.CollectionHelper.listOf;
import static org.mockito.Mockito.*;

public class TransactionServiceTests {

    private TransactionService service;
    private TransactionRepository mockedTransactionRepository;
    private StockRepository mockedStockRepository;

    @BeforeEach
    void beforeEach() {
        Mockito.clearAllCaches();
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
        String stockSymbol = "ABC";
        Transaction transaction = buildSuccessfulTestTransaction(stockSymbol, TransactionAction.BUY);

        when(mockedStockRepository.findBySymbol(stockSymbol)).thenReturn(Optional.ofNullable(transaction.getStock()));

        Transaction registeredTransaction = service.register(transaction, stockSymbol);

        assertThat(registeredTransaction).isNotNull();
        assertThat(registeredTransaction.getStock()).isEqualTo(transaction.getStock());
        assertThat(registeredTransaction.getAction()).isEqualTo(transaction.getAction());
        assertThat(registeredTransaction.getQuantity()).isEqualTo(transaction.getQuantity());
        assertThat(registeredTransaction.getUnitPrice()).isEqualTo(transaction.getUnitPrice());

        verify(mockedStockRepository, times(1)).findBySymbol(stockSymbol);
        verify(mockedTransactionRepository, never()).findByAccountId(transaction.getAccountId());
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 50})
    void sellStockPositionSuccessfully(int soldPosition) {
        String stockSymbol = "ABC";
        String accountId = generateRandomUUID();

        Transaction transactionBuy = buildSuccessfulTestTransaction(stockSymbol, TransactionAction.BUY, accountId);
        transactionBuy.setQuantity(100);

        Transaction transactionSell = buildSuccessfulTestTransaction(stockSymbol, TransactionAction.SELL, accountId);
        transactionSell.setQuantity(soldPosition);

        when(mockedStockRepository.findBySymbol(stockSymbol)).thenReturn(Optional.ofNullable(transactionBuy.getStock()));
        when(mockedTransactionRepository.findByAccountId(accountId)).thenReturn(listOf(transactionBuy));

        Transaction registeredTransaction = service.register(transactionSell, stockSymbol);

        assertThat(registeredTransaction).isNotNull();
        assertThat(registeredTransaction.getStock()).isEqualTo(transactionSell.getStock());
        assertThat(registeredTransaction.getAction()).isEqualTo(transactionSell.getAction());
        assertThat(registeredTransaction.getQuantity()).isEqualTo(transactionSell.getQuantity());
        assertThat(registeredTransaction.getUnitPrice()).isEqualTo(transactionSell.getUnitPrice());

        verify(mockedStockRepository, times(1)).findBySymbol(stockSymbol);
        verify(mockedTransactionRepository, times(1)).findByAccountId(accountId);
    }

    @Test
    void generateErrorIfStockIsNotAvailable() {
        String stockSymbol = "ABC";
        Stock nonExistentStock = new Stock(stockSymbol);
        Transaction transaction = new Transaction(nonExistentStock, TransactionAction.BUY);
        String expectedMessage = String.format("Could not find stock [Symbol: %s]", transaction.getSymbol());

        when(mockedStockRepository.findBySymbol(stockSymbol)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.register(transaction, stockSymbol)).hasMessage(expectedMessage);

        verify(mockedStockRepository, times(1)).findBySymbol(stockSymbol);
        verify(mockedTransactionRepository, never()).findByAccountId(transaction.getAccountId());
    }

    @Test
    @DisplayName("Register - Generate Error when selling a stock I've never bought")
    void generateErrorSellingOutOfStock_NeverBought() {
        String stockSymbol = "ABC";

        List<Transaction> previousTransactions = new ArrayList<>();

        Transaction transactionSell = buildSuccessfulTestTransaction(stockSymbol, TransactionAction.SELL);
        transactionSell.setQuantity(101);

        String accountId = transactionSell.getAccountId();

        when(mockedStockRepository.findBySymbol(stockSymbol)).thenReturn(Optional.ofNullable(transactionSell.getStock()));
        when(mockedTransactionRepository.findByAccountId(accountId)).thenReturn(previousTransactions);

        String expectedMessage = String.format("Transaction uses a Stock that is out of stock [Symbol: %s]", stockSymbol);
        assertThatThrownBy(() -> service.register(transactionSell, stockSymbol)).hasMessage(expectedMessage);

        verify(mockedStockRepository, times(1)).findBySymbol(stockSymbol);
        verify(mockedTransactionRepository, times(1)).findByAccountId(accountId);
    }

    @Test
    @DisplayName("Register - Generate Error when selling more stocks than I own")
    void generateErrorSellingOutOfStock_SellingMoreThanIOwn() {
        String stockSymbol = "ABC";
        String accountId = generateRandomUUID();

        Transaction transactionBuy = buildSuccessfulTestTransaction(stockSymbol, TransactionAction.BUY, accountId);
        transactionBuy.setQuantity(100);

        Transaction transactionSell1 = buildSuccessfulTestTransaction(stockSymbol, TransactionAction.SELL, accountId);
        transactionSell1.setQuantity(71);

        Transaction transactionSell2 = buildSuccessfulTestTransaction(stockSymbol, TransactionAction.SELL, accountId);
        transactionSell2.setQuantity(19);

        Transaction transactionSell3 = buildSuccessfulTestTransaction(stockSymbol, TransactionAction.SELL, accountId);
        transactionSell2.setQuantity(11);

        when(mockedStockRepository.findBySymbol(stockSymbol)).thenReturn(Optional.ofNullable(transactionBuy.getStock()));
        when(mockedTransactionRepository.findByAccountId(accountId)).thenReturn(listOf(transactionBuy, transactionSell1, transactionSell2, transactionSell3));

        String expectedMessage = String.format("Transaction uses a Stock that is out of stock [Symbol: %s]", stockSymbol);
        assertThatThrownBy(() -> service.register(transactionSell3, stockSymbol)).hasMessage(expectedMessage);

        verify(mockedStockRepository, times(1)).findBySymbol(stockSymbol);
        verify(mockedTransactionRepository, times(1)).findByAccountId(accountId);
    }

    @Test
    void listAccountTransactions() {
        String accountId = generateRandomUUID();
        Transaction transactionBuyAbc = buildSuccessfulTestTransaction(accountId, "ABC", 100, 10F, TransactionAction.BUY);
        Transaction transactionSellHalfAbc1 = buildSuccessfulTestTransaction(accountId, "ABC", 50, 11F, TransactionAction.SELL);
        Transaction transactionSellHalfAbc2 = buildSuccessfulTestTransaction(accountId, "ABC", 50, 15F, TransactionAction.SELL);
        Transaction transactionBuyDef = buildSuccessfulTestTransaction(accountId, "DEF", 200, 1.05F, TransactionAction.BUY);
        Transaction transactionBuyXyz = buildSuccessfulTestTransaction(accountId, "XYZ", 200, 5.79F, TransactionAction.BUY);

        List<Transaction> mockedTransactions = listOf(
                transactionBuyAbc,
                transactionSellHalfAbc1,
                transactionSellHalfAbc2,
                transactionBuyDef,
                transactionBuyXyz);

        when(mockedTransactionRepository.findByAccountId(accountId)).thenReturn(mockedTransactions);

        List<Transaction> transactions = service.listTransactions(accountId);

        assertThat(transactions).isEqualTo(mockedTransactions);
    }

    private Transaction buildSuccessfulTestTransaction(String stockSymbol, TransactionAction action) {
        return buildSuccessfulTestTransaction(stockSymbol, action, null);
    }

    private Transaction buildSuccessfulTestTransaction(String stockSymbol, TransactionAction action, String accountId) {
        int quantity = 100;
        float unitPrice = 10.5F;
        accountId = accountId == null ? generateRandomUUID() : accountId;
        return buildSuccessfulTestTransaction(accountId, stockSymbol, quantity, unitPrice, action);
    }

    private Transaction buildSuccessfulTestTransaction(String accountId, String stockSymbol, int quantity, float unitPrice, TransactionAction action) {
        Stock stock = new Stock(stockSymbol);

        Transaction transaction = new Transaction(stock, action);
        transaction.setAccountId(accountId);
        transaction.setQuantity(quantity);
        transaction.setUnitPrice(unitPrice);

        return transaction;
    }
}
