package com.transaction.manager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Transaction {
    @Id
    private Long id;
    private Double depositAmount;
    private Double withdrawalAmount;
    private Double savingsBalance;
    private String particulars;
    private TransactionType transactionType;
    private int domainStatusId;
}
