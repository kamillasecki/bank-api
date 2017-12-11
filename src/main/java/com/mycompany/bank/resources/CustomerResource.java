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
import javax.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CustomerResource {

    CustomerService customerService = new CustomerService();

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
        return customerService.validateCustomer(customer);
    }

//    @GET
//    @Path("/{login}")
//    public Customer getCustomer (@PathParam("login") String login){
//        return bankService.getCustomer(login);
//    }
}
