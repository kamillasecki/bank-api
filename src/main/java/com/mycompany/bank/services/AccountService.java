/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank.services;

import com.mycompany.bank.model.Account;
import com.mycompany.bank.model.Customer;
import com.mycompany.bank.model.Transaction;
import com.mycompany.bank.model.transactionType;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author klasecki
 */
public class AccountService {
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bank");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();
    
    public Account newAccount(int id, String token, String name){
        Customer test = em.find(Customer.class, id);
        if (test.getToken().equals(token)) {
            int accountNumber;
            Random rand = new Random();
            accountNumber = rand.nextInt(89999999) + 10000000;
            
            Account a = new Account();
            a.setAccNumber(accountNumber);
            a.setBalance(0);
            a.setName(name);
            a.setSortCode(905635);
            
            test.getAccount().add(a);
            
            tx.begin();
            em.persist(test);
            em.persist(a);
            tx.commit();
            em.close();
                    
            return a;   
        } else {
            return null;
        }
    }  
    
    public Account addMoney(int id, String token, Transaction t){
        Customer test = em.find(Customer.class, id);
        if (test.getToken().equals(token)) {
            List<Account> accounts = (List<Account>) test.getAccount();
            boolean found = false;
            int j = -1;
            if(!accounts.isEmpty()){
                for (int i=0;i<accounts.size();i++){
                    System.out.println("Checking provided account: " + t.getAccountNumber() + " if maches my account: " + accounts.get(i).getAccNumber());
                    if (accounts.get(i).getAccNumber() == t.getAccountNumber()){
                        found = true;
                        j = i;
                    }
                } 
                if (found){
                    t.setDescription("Top up");
                    t.setType(transactionType.TOPUP);
                    t.setDate(new Date());
                    
                    int newBalance = accounts.get(j).getBalance() + t.getAmount();
                    
                    t.setPostBallance(newBalance);
                    accounts.get(j).setBalance(newBalance);
                    accounts.get(j).getTransactions().add(t);
                    
                    tx.begin();
                    em.persist(test);
                    em.persist(t);
                    tx.commit();
                    em.close();
                } else {
                    return null;
                }
            } else {
                return null;
            }    
            return accounts.get(j);   
        } else {
            return null;
        }
    }
}
