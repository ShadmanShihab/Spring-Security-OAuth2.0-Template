package com.transaction.manager.service;

import com.transaction.manager.model.Transaction;

public interface TransactionProcessor {
    void processTransaction(Transaction transaction);
}
