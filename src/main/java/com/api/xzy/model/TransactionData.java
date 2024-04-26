package com.api.xzy.model;

public class TransactionData {

    private TblTransaction transaction;
    private String virtualAccount;
    private int statusVa;

    public TblTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(TblTransaction transaction) {
        this.transaction = transaction;
    }

    public String getVirtualAccount() {
        return virtualAccount;
    }

    public void setVirtualAccount(String virtualAccount) {
        this.virtualAccount = virtualAccount;
    }

    public int getStatusVa() {
        return statusVa;
    }

    public void setStatusVa(int statusVa) {
        this.statusVa = statusVa;
    }
}
