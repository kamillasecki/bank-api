/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank.services;

import com.mycompany.bank.model.Account;
import com.mycompany.bank.model.Customer;
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
            
            System.out.println(test.toString());
            
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
}
