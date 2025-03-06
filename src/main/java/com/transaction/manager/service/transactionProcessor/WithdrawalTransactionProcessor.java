package com.ecomm.shop.service.transactionProcessor;

import com.ecomm.shop.model.transaction.Transaction;

public class WithdrawalTransactionProcessor implements TransactionProcessor{

    @Override
    public void processTransaction(Transaction transaction) {
        System.out.println("Withdrawal transaction processor : " + transaction);
    }
}
