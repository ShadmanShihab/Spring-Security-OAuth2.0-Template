package com.ecomm.shop.components;

import com.ecomm.shop.model.transaction.TransactionType;
import com.ecomm.shop.service.transactionProcessor.DepositTransactionProcessor;
import com.ecomm.shop.service.transactionProcessor.TransactionProcessor;
import com.ecomm.shop.service.transactionProcessor.WithdrawalTransactionProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TransactionProcessorFactory {
    private final Map<TransactionType, TransactionProcessor> processorMap;

    @Autowired
    public TransactionProcessorFactory(List<TransactionProcessor> processors) {
        processorMap = new HashMap<>();
        for (TransactionProcessor processor : processors) {
            if (processor instanceof DepositTransactionProcessor) {
                processorMap.put(TransactionType.DEPOSIT, processor);
            } else if (processor instanceof WithdrawalTransactionProcessor) {
                processorMap.put(TransactionType.WITHDRAWAL, processor);
            }
        }
    }

    public TransactionProcessor getProcessor(TransactionType type) {
        return processorMap.get(type);
    }
}
