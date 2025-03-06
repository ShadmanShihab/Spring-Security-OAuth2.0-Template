package com.ecomm.shop.service.transactionProcessor;

import com.ecomm.shop.model.transaction.Transaction;

public interface TransactionProcessor {
    void processTransaction(Transaction transaction);
}
