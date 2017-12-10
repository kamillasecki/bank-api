/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank.resources;

import com.mycompany.bank.model.Customer;
import com.mycompany.bank.services.CustomerService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CustomerResource {

    CustomerService customerService = new CustomerService();

    @POST
    public Customer createCustomer (Customer customer){
        return customerService.createCustomer(customer);
    }

//    @GET
//    @Path("/{login}")
//    public Customer getCustomer (@PathParam("login") String login){
//        return bankService.getCustomer(login);
//    }
}
