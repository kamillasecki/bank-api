package com.mycompany.bank.model;
/**
 * Address.java
 * Version Rev1
 * Date 12/11/2017
 * @author Kamil Lasecki, x14100819
 */
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;

@Embeddable // Tells hibernate that this class is embedded as part of another class
public class Address {

    
    private String street;
    private String city;
    private String county;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }


}
