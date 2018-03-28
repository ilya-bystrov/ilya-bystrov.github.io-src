package io.github.ilyabystrov.demo.restmoneytransferservice.resource;

import io.github.ilyabystrov.demo.restmoneytransferservice.domain.Account;
import io.github.ilyabystrov.demo.restmoneytransferservice.service.AccountServce;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
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
//    @Produces("text/plain")
    @Produces(MediaType.APPLICATION_JSON)
    public Account getAccount(@PathParam("{accountId") Long accountId) {
//    final Account account = accountService.getAccount(accountId);
    final Account account = new Account();
      return account;
    }
}