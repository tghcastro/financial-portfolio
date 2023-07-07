package com.tghcastro.financialportfolio.stocksservice.controller;

import com.tghcastro.financialportfolio.stocksservice.controller.contracts.request.PostTransactionBody;
import com.tghcastro.financialportfolio.stocksservice.domain.Transaction;
import com.tghcastro.financialportfolio.stocksservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transaction")
    List<Transaction> listAll(@RequestHeader(value = "x-account-id") String accountId) {
        return transactionService.listTransactions(accountId);
    }

    @PostMapping("/transaction/register")
    Transaction createNew(@RequestHeader(value = "x-account-id") String accountId, @RequestBody PostTransactionBody transactionData) {

        Transaction transaction = new Transaction(transactionData.symbol(), transactionData.action());
        transaction.setUnitPrice(transactionData.unitPrice());
        transaction.setTotalCosts(transactionData.totalCosts());
        transaction.setQuantity(transactionData.quantity());
        transaction.setExecutionDate(transactionData.executionDate());
        transaction.setAccountId(accountId);
        transaction.setRegisterDateDate(Instant.now());

        return transactionService.register(transaction, transactionData.symbol());
    }

}
