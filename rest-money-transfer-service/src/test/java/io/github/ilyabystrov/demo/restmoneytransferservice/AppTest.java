package io.github.ilyabystrov.demo.restmoneytransferservice;

import io.github.ilyabystrov.demo.restmoneytransferservice.domain.Account;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

/**
 *
 * @author Ilya Bystrov @iliabystrov
 */
public class AppTest extends JerseyTest {
  
  @Override
  protected Application configure() {
    return App.createResourceConfig();
  }
  
  
  @Test
  public void testMain() {
    Object account1 = target("account/1").request().get(Account.class);
    System.out.println("account1 = " + account1);
  }
  
}
