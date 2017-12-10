package com.mycompany.bank.database;

import com.mycompany.bank.model.Customer;
import java.util.HashMap;
        import java.util.Map;

public class DatabaseClass {

    private static Map<Long, Customer> customers = new HashMap<>();


    public static Map<Long, Customer> getBooks() {
        return customers;
    }
}
