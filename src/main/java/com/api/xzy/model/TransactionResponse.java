package com.api.xzy.model;

public class TransactionResponse {

    private int transactionId;
    private int virtualaccount;
    private int price;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getVirtualaccount() {
        return virtualaccount;
    }

    public void setVirtualaccount(int virtualaccount) {
        this.virtualaccount = virtualaccount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
