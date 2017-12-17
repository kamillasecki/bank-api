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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

/**
 *
 * @author kamil
 */
public class TransactionService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bank");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();

   

    public boolean validateToken(int id, String token) {
        Customer test = em.find(Customer.class, id);
        if (test.getToken().equals(token)) {
            return true;
        } else {
            return false;
        }
    }


    public Response getAccoumtTransactions(int id, long acc, String token) {
        Customer test = em.find(Customer.class, id);
        //authenticate token
        if (test.getToken().equals(token)) {
            //get all user's accounts
            List<Account> accounts = (List<Account>) test.getAccount();
            boolean found = false;
            int j = -1;

            //check if user have any accounts
            if (!accounts.isEmpty()) {
                //check id requested account exists for the user
                for (int i = 0; i < accounts.size(); i++) {
                    if (accounts.get(i).getAccNumber() == acc) {
                        found = true;
                        j = i;
                    }
                }

                //account ok
                if (found) {
                    Account a = em.find(Account.class, acc);
                    GenericEntity<List<Transaction>> list = new GenericEntity<List<Transaction>>((List<Transaction>) a.getTransactions()) {
                    };

                    return Response.status(Response.Status.OK).entity(list).build();
                } else {
                    //bad account
                    String text = "{\"text\":\"Requested account does not exist or does'n belong to you.\"}";
                    return Response.status(Response.Status.UNAUTHORIZED).entity(text).build();
                }
            } else {
                //no accounts
                String text = "{\"text\":\"You dont have any accounts yet. Please create the account first.\"}";
                return Response.status(Response.Status.UNAUTHORIZED).entity(text).build();
            }
        } else {
            //unauthorised token
            String text = "{\"text\":\"Incorrect token or not logged in.\"}";
            return Response.status(Response.Status.UNAUTHORIZED).entity(text).build();
        }

    }

    public Response getAccoumtTransaction(int id, long acc, int trx, String token) {

        if (validateToken(id, token)) {
            Account a = em.find(Account.class, acc);
            if (a != null) {

                List<Transaction> trxList = (List<Transaction>) a.getTransactions();

                if (!trxList.isEmpty()) {
                    for (int i = 0; i < trxList.size(); i++) {
                        if (trxList.get(i).getId() == trx) {
                            return Response.status(Response.Status.OK).entity(trxList.get(i)).build();
                        }
                    }
                } else {
                    return Response.status(Response.Status.OK).entity(null).build();
                }

                return Response.status(Response.Status.OK).entity(trx).build();
            } else {
                String text = "{\"text\":\"Account does not exist.\"}";
                return Response.status(Response.Status.UNAUTHORIZED).entity(text).build();
            }
        } else {
            String text = "{\"text\":\"Wrong token or not logged in.\"}";
            return Response.status(Response.Status.UNAUTHORIZED).entity(text).build();
        }

    }

    
}
