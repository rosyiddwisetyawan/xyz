package com.api.xzy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "tbl_virtualaccount")
public class TblVirtualaccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "virtualaccount", nullable = false, length = 45)
    private String virtualaccount;

    @Column(name = "transactionId", nullable = false)
    private int transactionId;

    @Column(name = "status")
    private int status;

    @Column(name = "createddate")
    private Timestamp createddate;

    @Column(name = "transactiondate")
    private Timestamp transactiondate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVirtualaccount() {
        return virtualaccount;
    }

    public void setVirtualaccount(String virtualaccount) {
        this.virtualaccount = virtualaccount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Timestamp createddate) {
        this.createddate = createddate;
    }

    public Timestamp getTransactiondate() {
        return transactiondate;
    }

    public void setTransactiondate(Timestamp transactiondate) {
        this.transactiondate = transactiondate;
    }

}