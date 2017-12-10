/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank.model;


import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@XmlRootElement
public class Customer implements Serializable {
    @Id
    private String name;
    private String address;
    private String email;
    private String login;
    //private List <Account> accounts;

    public Customer() {}

    public Customer(String name, String address, String email, String login) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.login = login;
        //this.accounts = accounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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

//    public List<Account> getAccounts() {
//        return accounts;
//    }
//
//    public void addAccount(Account account) {
//        this.accounts.add(account);
//    }
}
