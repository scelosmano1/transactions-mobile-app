package com.example.rma20celosmanovicselma04;

import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private Double amount;
    private String title;
    private TransactionType type;
    private String itemDescription;
    private Integer transactionInterval;
    private LocalDate endDate;

    public Transaction(LocalDate date, Double amount, String title, TransactionType type, String itemDescription, Integer transactionInterval, LocalDate endDate) {
        this.amount = amount;
        this.title = title;
        this.date = date;
        this.type = type;
        if(type.equals(TransactionType.INDIVIDUALINCOME)) this.itemDescription = null;
        else this.itemDescription = itemDescription;

        if(type.equals(TransactionType.REGULARINCOME) || type.equals(TransactionType.REGULARPAYMENT)) this.transactionInterval = transactionInterval;
        else this.transactionInterval = null;

        if(type.equals(TransactionType.REGULARPAYMENT) || type.equals(TransactionType.REGULARINCOME)) this.endDate = endDate;
        else this.endDate = null;
    }

    public Transaction() {
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getTransactionInterval() {
        return transactionInterval;
    }

    public void setTransactionInterval(int transactionInterval) {
        this.transactionInterval = transactionInterval;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
