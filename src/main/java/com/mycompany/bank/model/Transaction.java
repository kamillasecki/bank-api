package com.mycompany.bank.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Transaction {

    public Transaction(double amount, long accountNumber, String description, double postBallance) {
        
        //DR or CR transaction automatically
        if(amount>0){
            this.type = type.DR;
        }
        else{
            this.type = type.CR;
        }
        
        
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.date = new Date(); //generating date automatically
        this.description = description;
        this.postBallance = postBallance;
    }

    private transactionType type;
    private double amount;
    private long accountNumber;
    private Date date;
    private String description;
    private double postBallance;

    public transactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public long getAccountNumber() {
        return accountNumber;
    }


    public Date getDate() {
        return date;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPostBallance() {
        return postBallance;
    }

    public void setPostBallance(double postBallance) {
        this.postBallance = postBallance;
    }

}
