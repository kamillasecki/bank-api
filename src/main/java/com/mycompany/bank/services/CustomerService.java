/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank.services;

import com.mycompany.bank.model.Account;
import com.mycompany.bank.model.Customer;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;

public class CustomerService {
    
    //===========================================
    //=         Attributes
    //===========================================
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cusromer");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();        

  public boolean customerExists(String login) {
       String query = "Select id from Customer c where c.login ='" + login + "'";
       Query test = em.createNativeQuery(query);
       List results = test.getResultList();
        return !results.isEmpty();
  }
    
    public Customer createCustomer(Customer customer){
        Customer test = em.find(Customer.class, customer.getId());
        System.out.println(customer.toString());
        if (test == null) {
            tx.begin();
            em.persist(customer);
            tx.commit();
            em.close();
        }
        return customer;
    }
    
    public Response validateCustomer(Customer customer) {
        Query q = em.createQuery("SELECT c from Customer c WHERE c.login = :username");
        q.setParameter("username", customer.getLogin());
        List<Customer> results = q.getResultList();
        if(!results.isEmpty()){
           Customer fromDb = results.get(0);
           System.out.println(fromDb.toString() + " : " + customer.getPassword());
           if (!fromDb.getPassword().equals(customer.getPassword())) {
               return Response.status(Response.Status.FORBIDDEN).entity("Invalid username or password!").build();
           } else {
               Random random = new SecureRandom();
               String token = new BigInteger(130, random).toString(32);
               
               tx.begin();
               em.createQuery("UPDATE Customer c SET c.token = :token WHERE c.id = :id")
                       .setParameter("token", token)
                       .setParameter("id", fromDb.getId())
                       .executeUpdate();
               tx.commit();
               em.close();
               
               return Response.status(Response.Status.OK).entity(token).build();
           }
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("Invalid username or password").build();
        }
    }
    public Customer getCustomer (int id, String token){
        Customer test = em.find(Customer.class, id);
        if (test.getToken().equals(token)) {
            return test;   
        } else {
            return null;
        }
    }

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
