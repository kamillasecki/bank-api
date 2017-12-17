/**
 * CustomerService.java
 * Version Rev3
 * Date 14/12/2017
 *
 * @author Kamil Lasecki, x14100819
 */
package com.mycompany.bank.services;

import com.mycompany.bank.model.Customer;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.core.Response;

public class CustomerService {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bank");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();

    public boolean customerExists(String login) {
        String query = "Select id from Customer c where c.login ='" + login + "'";
        Query test = em.createNativeQuery(query);
        List results = test.getResultList();
        return !results.isEmpty();
    }

    public Customer createCustomer(Customer customer) {
        Customer test = em.find(Customer.class, customer.getId());
        if (test == null) {
            tx.begin();
            em.persist(customer);
            tx.commit();
            em.close();
        }
        return customer;
    }

    public Customer validateCustomer(Customer customer) {
        Query q = em.createQuery("SELECT c from Customer c WHERE c.login = :username");
        q.setParameter("username", customer.getLogin());
        List<Customer> results = q.getResultList();
        if (!results.isEmpty()) {
            Customer fromDb = results.get(0);
            System.out.println(fromDb + " : " + customer.getPassword());
            if (!fromDb.getPassword().equals(customer.getPassword())) {
                //return Response.status(Response.Status.FORBIDDEN).entity("{'text':'Invalid username or password!'}").build();
                return null;
            } else {
                Random random = new SecureRandom();
                String token = new BigInteger(130, random).toString(32);

                tx.begin();
                int executeUpdate = em.createQuery("UPDATE Customer c SET c.token = :token WHERE c.id = :id")
                        .setParameter("token", token)
                        .setParameter("id", fromDb.getId())
                        .executeUpdate();
                tx.commit();

                Customer c = new Customer();
                c.setEmail(fromDb.getEmail());
                c.setLogin(fromDb.getLogin());
                c.setToken(token);
                c.setId(fromDb.getId());

                return c;
            }
        } else {
            return null;
        }
    }

    public Customer getCustomer(int id, String token) {
        Customer test = em.find(Customer.class, id);
        if (test.getToken().equals(token)) {
            return test;
        } else {
            return null;
        }
    }

    public Response destroySession(int id, String token) {
        Customer test = em.find(Customer.class, id);
        if (test.getToken().equals(token)) {
            Random random = new SecureRandom();
            String secretToken = new BigInteger(130, random).toString(32);

            test.setToken(secretToken);

            tx.begin();
            em.persist(test);
            tx.commit();
            em.close();
            String text = "{\"text\":\"User logged out successfuly.\"}";
            return Response.status(Response.Status.OK).entity(text).build();
        } else {
            String text = "{\"text\":\"Incorrect token or user.\"}";
            return Response.status(Response.Status.FORBIDDEN).entity(text).build();
        }
    }
    
    
    
    public Response setCustomer(int id, Customer newDetails, String token){
    
        Customer test = em.find(Customer.class, id);

        if (test.getToken().equals(token)) {
            
            //mapping new details against customer
            
            
           
            if(newDetails.getAddress() != null)
                test.setAddress(newDetails.getAddress());
            if(newDetails.getEmail() != null)
                test.setEmail(newDetails.getEmail());
            if(newDetails.getName() != null)
                test.setName(newDetails.getName());
            
            tx.begin();
            em.persist(test);
            tx.commit();
            
            return Response.status(Response.Status.OK).entity(test).build();
                        
            
        } else {
            String text = "{\"text\":\"Incorrect token or user.\"}";
            return Response.status(Response.Status.FORBIDDEN).entity(text).build();
        }

    }
    
    public Response setPassword(int id, Customer c, String token) {
        Customer test = em.find(Customer.class, id);

        if (test.getToken().equals(token)) {
            if(c.getPassword() != null)
                test.setPassword(c.getPassword());
            tx.begin();
            em.persist(test);
            tx.commit();
            
            return Response.status(Response.Status.OK).entity(test).build();
                        
            
        } else {
            String text = "{\"text\":\"Incorrect token or user.\"}";
            return Response.status(Response.Status.FORBIDDEN).entity(text).build();
        }
    }
 
}
