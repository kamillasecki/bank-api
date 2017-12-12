/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@XmlRootElement
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE) 
    private int id;
    private long sortCode;
    private long accNumber;
    private int balance;
    private String name;
    //private List <Transaction> list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account() {
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

//    public List<Transaction> getList() {
//        return list;
//    }
//
//    public void setList(List<Transaction> list) {
//        this.list = list;
//    }
}
