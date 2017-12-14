package com.mycompany.bank.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@XmlRootElement
public class Transaction {
    
    @Id
    @GeneratedValue
    private int id;
    private transactionType type;
    private int amount;
    private long accountNumber;
    private Date date;
    private String description;
    private int postBallance;
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public transactionType getType() {
        return type;
    }

    public void setType(transactionType type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPostBallance() {
        return postBallance;
    }

    public void setPostBallance(int postBallance) {
        this.postBallance = postBallance;
    }
}
