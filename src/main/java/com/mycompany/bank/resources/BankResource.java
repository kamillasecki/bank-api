/**
 * CustomerResource.java
 * Version Rev1
 * Date 12/12/2017
 *
 * @author Kamil Lasecki, x14100819
 */
package com.mycompany.bank.resources;

import com.mycompany.bank.model.Account;
import com.mycompany.bank.model.Customer;
import com.mycompany.bank.model.Transaction;
import com.mycompany.bank.services.AccountService;
import com.mycompany.bank.services.CustomerService;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class BankResource {

    CustomerService customerService = new CustomerService();
    AccountService accountService = new AccountService();

    @POST
    @Path("/registration")
    public Response createCustomer(Customer customer) {
        if (customerService.customerExists(customer.getLogin())) {
            String text = "{\"text\":\"Customer with login " + customer.getLogin() + " already exist.\"}";
            return Response.status(Response.Status.FORBIDDEN).entity(text).build();
        } else {
            return Response.status(Response.Status.OK).entity(customerService.createCustomer(customer)).build();
        }
    }

    @POST
    @Path("/login")
    public Response login(Customer customer) {
        Customer c = customerService.validateCustomer(customer);
        if (c != null) {
            return Response.status(Response.Status.OK).entity(c).build();
            //System.out.println(c);
        } else {
            String text = "{\"text\":\"Incorrect username or password.\"}";
            return Response.status(Response.Status.FORBIDDEN).entity(text).build();
        }
    }

    @GET
    @Path("/{id}/logout")
    public Response destroySession(Transaction transaction, @PathParam("id") int id, @Context HttpHeaders headers) {
        List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        return customerService.destroySession(id, authHeaders.get(0));
    }

    @GET
    @Path("/{id}")
    public Response getCustomer(@PathParam("id") int id, @Context HttpHeaders headers) {
        List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        Customer c = customerService.getCustomer(id, authHeaders.get(0));
        if (c == null) {
            String text = "{\"text\":\"Bad token or user not logged in.\"}";
            return Response.status(Response.Status.FORBIDDEN).entity(text).build();
        } else {
            return Response.status(Response.Status.OK).entity(c).build();
        }
    }

    @POST
    @Path("/{id}/account")
    public Response newAccount(Account account, @PathParam("id") int id, @Context HttpHeaders headers) {
        List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        return accountService.newAccount(id, authHeaders.get(0), account.getName());
    }

    @POST
    @Path("/{id}/account/{acc}")
    public Response addMoney(Transaction transaction, @PathParam("id") int id, @PathParam("acc") int acc, @Context HttpHeaders headers) {
        transaction.setAccountNumber(acc);
        List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        return accountService.addMoney(id, authHeaders.get(0), transaction);
    }

    @GET
    @Path("/{id}/account/{acc}")
    public Response getAccount(@PathParam("id") int id, @PathParam("acc") long acc, @Context HttpHeaders headers) {
        List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        Account a = accountService.getAccoumt(id, acc, authHeaders.get(0));
        if (a == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized action").build();
        } else {
            return Response.status(Response.Status.OK).entity(a).build();
        }
    }

    @POST
    @Path("/{id}/account/{acc}/transfer")
    public Response sendMoney(Transaction transaction, @PathParam("id") int id, @PathParam("acc") long acc, @Context HttpHeaders headers) {
        List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
        return accountService.sendMoney(id, acc, authHeaders.get(0), transaction);
    }
}
