package io.github.ilyabystrov.demo.restmoneytransferservice.resource;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

public class ExceptionLogger implements ApplicationEventListener, RequestEventListener {
  
  @Override
  public void onEvent(final ApplicationEvent applicationEvent) {
  }

  @Override
  public RequestEventListener onRequest(final RequestEvent requestEvent) {
    return this;
  }

  @Override
  public void onEvent(RequestEvent paramRequestEvent) {
    if (paramRequestEvent.getType() == RequestEvent.Type.ON_EXCEPTION) {
      Logger.getLogger(ExceptionLogger.class.getName()).log(Level.SEVERE, null, paramRequestEvent.getException());
    }
  }
}
