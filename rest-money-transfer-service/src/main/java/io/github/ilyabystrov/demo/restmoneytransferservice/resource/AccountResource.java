package io.github.ilyabystrov.demo.restmoneytransferservice.resource;

import io.github.ilyabystrov.demo.restmoneytransferservice.domain.Account;
import io.github.ilyabystrov.demo.restmoneytransferservice.service.AccountServce;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/account")
@Singleton
public class AccountResource {
  
  @Inject
  private AccountServce accountService;
  
  @GET
  @Path("{accountId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Account getAccount(@PathParam("accountId") Long accountId) {
    return accountService.getAccount(accountId);
  }
  
  @POST
  @Path("transfer")
  @Consumes(MediaType.APPLICATION_JSON)
  public void transfer(TransferRequest transferRequest) {
    accountService.transferMoney(transferRequest.getSender(), transferRequest.getRecipient(), transferRequest.getAmount());
  }
}