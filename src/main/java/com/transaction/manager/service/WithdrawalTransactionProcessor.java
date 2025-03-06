package com.transaction.manager.service;

import com.transaction.manager.model.Transaction;

public class WithdrawalTransactionProcessor implements TransactionProcessor{

    @Override
    public void processTransaction(Transaction transaction) {
        System.out.println("Withdrawal transaction processor : " + transaction);
    }
}
