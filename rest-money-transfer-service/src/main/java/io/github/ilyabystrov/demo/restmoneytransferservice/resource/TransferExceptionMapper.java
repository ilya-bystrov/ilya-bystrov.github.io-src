package io.github.ilyabystrov.demo.restmoneytransferservice.resource;

import io.github.ilyabystrov.demo.restmoneytransferservice.service.TransferException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TransferExceptionMapper implements ExceptionMapper<TransferException> {
  
  @Override
  public Response toResponse(TransferException ex) {
    return Response.status(Status.CONFLICT).entity(ex.getMessage()).type(MediaType.TEXT_PLAIN).build();
  }
}
