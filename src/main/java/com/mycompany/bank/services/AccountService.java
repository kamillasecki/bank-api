/**
 * AccountService.java
 * Version Rev1
 * Date 12/12/2017
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

public class AccountService {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bank");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();

    public Account newAccount(int id, String token, String name) {
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

    public Account addMoney(int id, String token, Transaction t) {
        Customer test = em.find(Customer.class, id);
        if (test.getToken().equals(token)) {
            List<Account> accounts = (List<Account>) test.getAccount();
            boolean found = false;
            int j = -1;
            if (!accounts.isEmpty()) {
                for (int i = 0; i < accounts.size(); i++) {
                    System.out.println("Checking provided account: " + t.getAccountNumber() + " if maches my account: " + accounts.get(i).getAccNumber());
                    if (accounts.get(i).getAccNumber() == t.getAccountNumber()) {
                        found = true;
                        j = i;
                    }
                }
                if (found) {
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
}
