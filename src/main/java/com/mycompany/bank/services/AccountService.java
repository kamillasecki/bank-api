/**
 * AccountService.java
 * Version Rev1
 * Date 12/12/2017
 *
 * @author Kamil Lasecki, x14100819
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
import javax.ws.rs.core.Response;

public class AccountService {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bank");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();

    public Response newAccount(int id, String token, String name) {
        //Authenticate user
        Customer test = em.find(Customer.class, id);

        if (test.getToken().equals(token)) {
            //Get all users account

            List<Account> accounts = (List<Account>) test.getAccount();
            //Checking if account with such a name already exists
            boolean found = false;
            for (int i = 0; i < accounts.size(); i++) {
                if (accounts.get(i).getName().equals(name)) {
                    found = true;
                }
            }

            if (!found) {
                //Create random account number
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

                return Response.status(Response.Status.OK).entity(a).build();
            } else {
                String text = "{\"text\":\"Account with such a name already exists.\"}";
                return Response.status(Response.Status.UNAUTHORIZED).entity(text).build();
            }

        } else {
            //user unauthorised
            String text = "{\"text\":\"Invalid token or user not logged in.\"}";
            return Response.status(Response.Status.UNAUTHORIZED).entity(text).build();
        }
    }

    public Response addMoney(int id, String token, Transaction t) {
        Customer test = em.find(Customer.class, id);
        //check if amount is valid
        if (t.getAmount() > 0) {
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
                        System.out.println("Checking provided account: " + t.getAccountNumber() + " if maches my account: " + accounts.get(i).getAccNumber());
                        if (accounts.get(i).getAccNumber() == t.getAccountNumber()) {
                            found = true;
                            j = i;
                        }
                    }

                    //account ok
                    if (found) {
                        //create transaction
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
                        //bad account
                        String text = "{\"text\":\"Requested account does not exist or does'n belong to you.\"}";
                        return Response.status(Response.Status.UNAUTHORIZED).entity(text).build();
                    }
                } else {
                    //no accounts
                    String text = "{\"text\":\"You dont have any accounts yet. Please create the account first.\"}";
                    return Response.status(Response.Status.UNAUTHORIZED).entity(text).build();
                }
                return Response.status(Response.Status.OK).entity(accounts.get(j)).build();   
            } else {
                //unauthorised token
                String text = "{\"text\":\"Incorrect token or not logged in.\"}";
                return Response.status(Response.Status.UNAUTHORIZED).entity(text).build();
            }
        } else {
            //amonut not a number
            String text = "{\"text\":\"Requested amount is incorrect.\"}";
            return Response.status(Response.Status.UNAUTHORIZED).entity(text).build();
        }
    }

    public Account sendMoney(int id, long sendAcc, String token, Transaction t) {
        //Get sender from DB
        Customer test = em.find(Customer.class, id);
        long senderAcc = sendAcc;
        long receiverAcc = t.getAccountNumber();
        //Check authorisation token
        if (test.getToken().equals(token)) {
            //Get all user's accounts
            List<Account> accounts = (List<Account>) test.getAccount();
            boolean found = false;
            //check if requested account belongs to the user
            if (!accounts.isEmpty()) {
                for (int i = 0; i < accounts.size(); i++) {
                    System.out.println("Checking provided account: " + senderAcc + " if maches my account: " + accounts.get(i).getAccNumber());
                    if (accounts.get(i).getAccNumber() == senderAcc) {
                        found = true;
                    }
                }
                if (found) {
                    //find destination account
                    Account toAcc = em.find(Account.class, receiverAcc);
                    Account fromAcc = em.find(Account.class, senderAcc);
                    if (toAcc != null) {
                        //substract money from senders account
                        t.setType(transactionType.DEBIT);
                        t.setAmount(t.getAmount() * -1);
                        t.setDate(new Date());
                        t.setAccountNumber(senderAcc);
                        int newBalance = fromAcc.getBalance() + t.getAmount();
                        t.setPostBallance(newBalance);

                        //set a new ballance on the sender's account
                        fromAcc.setBalance(newBalance);
                        //Add the transaction to sender's account
                        fromAcc.getTransactions().add(t);

                        Transaction t2 = new Transaction();
                        //add money to receiver's account
                        t2.setType(transactionType.CREDIT);
                        t2.setAmount(t.getAmount() * -1);
                        t2.setDate(new Date());
                        t2.setAccountNumber(receiverAcc);
                        newBalance = toAcc.getBalance() + t2.getAmount();
                        t2.setPostBallance(newBalance);

                        //set a new ballance on the receiver's account
                        toAcc.setBalance(newBalance);
                        //Add the transaction to receiver's account
                        toAcc.getTransactions().add(t2);

                        tx.begin();
                        em.persist(toAcc);
                        em.persist(fromAcc);
                        em.persist(t);
                        em.persist(t2);
                        tx.commit();

                        return fromAcc;

                    } else {
                        //destination account not found
                        return null;
                    }

                } else {
                    //sender does not own the account he is using
                    return null;
                }
            } else {
                //user does not have any accounts
                return null;
            }
        } else {
            //token does not match account
            return null;
        }
    }

    public boolean validateToken(int id, String token) {
        Customer test = em.find(Customer.class, id);
        if (test.getToken().equals(token)) {
            return true;
        } else {
            return false;
        }
    }

    public Account getAccoumt(int id, long acc, String token) {
        if (validateToken(id, token)) {

        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
