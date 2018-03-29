package io.github.ilyabystrov.demo.restmoneytransferservice.resource;

import io.github.ilyabystrov.demo.restmoneytransferservice.service.AccountNotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccountNotFoundExceptionMapper implements ExceptionMapper<AccountNotFoundException> {
  
  @Override
  public Response toResponse(AccountNotFoundException ex) {
    return Response.status(Status.NOT_FOUND).entity(ex.getMessage()).type(MediaType.TEXT_PLAIN).build();
  }
}
