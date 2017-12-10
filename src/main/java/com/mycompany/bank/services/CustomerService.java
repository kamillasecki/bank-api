/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank.services;

import com.mycompany.bank.model.Account;
import com.mycompany.bank.model.Customer;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class CustomerService {
    
    //===========================================
    //=         Attributes
    //===========================================
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cusromer");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();        

  
    public Customer createCustomer(Customer customer){
        Customer test = em.find(Customer.class, customer.getName());
        if (test == null) {
            tx.begin();
            em.persist(customer);
            tx.commit();
            
            em.close();
        }
//        boolean found = false;
//        String status;
//
//        for (Map.Entry<Long, Customer> entry : customers.entrySet())
//        {
//            if (customer.getLogin() == entry.getValue().getLogin()){
//                found = true;
//            }
//        }
//        if (found) {
//           // status = "{\"status\":\"The customer with login: " + customer.getLogin() + " already exists\"}";
//        } else {
//            customers.put((long) (customers.size()+1), customer);
//            //status = "{\"status\":\"The customer " + customer.getLogin() + " has been added successfully\"}";
//        }
        //customers.put((long) (customers.size()+1), customer);
        return customer;
    }

//    public Customer getCustomer (String login){
//        boolean found = false;
//        long key = 0;
//        for (Map.Entry<Long, Customer> entry : customers.entrySet())
//        {
//            if (login.equals(entry.getValue().getLogin())){
//                found = true;
//                key = entry.getKey();
//            }
//        }
//        if (found) {
//            return customers.get(key);
//        } else {
//            return null;
//        }
//    }

//    public String addAccount(Customer owner, Account account){
//
//        int accountNumber;
//        boolean found = false;
//        do {
//            Random rand = new Random();
//            accountNumber = rand.nextInt(89999999) + 10000000;
//
//            for (Map.Entry<Long, Customer> entry : customers.entrySet()) {
//                for (Account element : entry.getValue().getAccounts()) {
//                    if (element.getAccNumber() == accountNumber) {
//                        found = true;
//                    }
//                }
//            }
//        } while (found);
//        account.setAccNumber(accountNumber);
//        owner.addAccount(account);
//        return "{\"status\":\"account number: " + accountNumber + " has been created\"}";
//    }

//    public List<Account> displayAccounts(Customer user){
//        Customer customer = null;
//        for (Map.Entry<Long, Customer> entry : customers.entrySet())
//        {
//            if (user == entry.getValue()){
//                customer=entry.getValue();
//            }
//        }
//        return customer.getAccounts();
//    }

}
