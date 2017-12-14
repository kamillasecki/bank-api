/**
 * Customer.java
 * Version Rev1
 * Date 12/11/2017
 * @author Kamil Lasecki, x14100819
 */
package com.mycompany.bank.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
@XmlRootElement
public class Customer implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE) 
    private int id;
    private String name;
    @Embedded
    private Address address;
    private String email;
    private String login;
    private String password;
    private String token;
    @OneToMany
    private Collection<Account> account = new ArrayList<Account>();
    
    
    public Collection<Account> getAccount() {
        return account;
    }

    public void setAccount(Collection<Account> account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
