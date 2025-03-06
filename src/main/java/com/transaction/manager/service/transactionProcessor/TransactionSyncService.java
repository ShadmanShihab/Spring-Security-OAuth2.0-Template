package com.ecomm.shop.service.transactionProcessor;

import com.ecomm.shop.components.TransactionProcessorFactory;
import com.ecomm.shop.model.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionSyncService {
    private final TransactionProcessorFactory processorFactory;

    @Autowired
    public TransactionSyncService(TransactionProcessorFactory processorFactory) {
        this.processorFactory = processorFactory;
    }

    public void syncTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            TransactionProcessor processor =
                    processorFactory.getProcessor(transaction.getTransactionType());
            if (processor != null) {
                processor.processTransaction(transaction);
            } else {
                throw new IllegalArgumentException(
                        "Unknown transaction type: " + transaction.getTransactionType());
            }
        }
    }
}
