package com.ecomm.shop.service.transactionProcessor;

import com.ecomm.shop.model.transaction.Transaction;

public class DepositTransactionProcessor implements TransactionProcessor{

    @Override
    public void processTransaction(Transaction transaction) {
        System.out.println("Processing Deposit Transaction: " + transaction);
    }
}
