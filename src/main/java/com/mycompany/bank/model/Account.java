package com.mycompany.bank.model;

/**
 * Account.java
 * Version Rev1
 * Date 12/11/2017
 * @author Kamil Lasecki, x14100819
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Account implements Serializable {
    @Id
    private long accNumber;
    private long sortCode;
    private int balance;
    private String name;
    @OneToMany
    private Collection<Transaction> transactions = new ArrayList<Transaction>();

    public Collection<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Collection<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSortCode() {
        return sortCode;
    }

    public void setSortCode(long sortCode) {
        this.sortCode = sortCode;
    }

    public long getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(long accNumber) {
        this.accNumber = accNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}
