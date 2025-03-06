package com.transaction.manager.service;

import com.transaction.manager.model.Transaction;

public class DepositTransactionProcessor implements TransactionProcessor{

    @Override
    public void processTransaction(Transaction transaction) {
        System.out.println("Processing Deposit Transaction: " + transaction);
    }
}
