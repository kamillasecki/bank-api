/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank.resources;

import com.mycompany.bank.model.Account;
import com.mycompany.bank.model.Customer;
import com.mycompany.bank.services.AccountService;
import com.mycompany.bank.services.CustomerService;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CustomerResource {

    CustomerService customerService = new CustomerService();
    AccountService accountService = new AccountService();

    @POST
    @Path("/registration")
    public Response createCustomer (Customer customer){
        if(customerService.customerExists(customer.getLogin())){
            return Response.status(Response.Status.FORBIDDEN).entity("Customer with login " + customer.getLogin() + " already exist.").build();
        } else {
            return Response.status(Response.Status.OK).entity(customerService.createCustomer(customer)).build();
        }
        
    }
    
    @POST
    @Path("/login")
    public Response login (Customer customer){
        Customer c = customerService.validateCustomer(customer);
        if (c != null){
            return Response.status(Response.Status.OK).entity(c).build();
            //System.out.println(c);
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("Incorrect username or password.").build();
        }
    }

    @GET
    @Path("/user/{id}")
    public Response getCustomer (@PathParam("id") int id, @Context HttpHeaders headers){
        List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("hello");
        System.out.println("HEADERS: " + authHeaders.get(0));
        System.out.println("ID: " + id);
        Customer c = customerService.getCustomer(id,authHeaders.get(0));
        if(c == null){
            return Response.status(Response.Status.FORBIDDEN).entity("Bad token or user not logged in").build();
        } else {
            return Response.status(Response.Status.OK).entity(c).build();
        }
    }
    
    @POST
    @Path("/user/{id}/account/new")
    public Response newAccount (Account account, @PathParam("id") int id, @Context HttpHeaders headers){
        List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("hello");
        System.out.println("HEADERS: " + authHeaders.get(0));
        System.out.println("ID: " + id);
        Account a = accountService.newAccount(id, authHeaders.get(0), account.getName());
        if (a == null) {
            return Response.status(Response.Status.FORBIDDEN).entity("Something wet wrong").build();
        } else {
            return Response.status(Response.Status.OK).entity(a).build();
        }
    }
}
