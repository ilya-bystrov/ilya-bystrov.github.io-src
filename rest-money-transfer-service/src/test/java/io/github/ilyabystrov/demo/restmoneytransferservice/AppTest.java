package io.github.ilyabystrov.demo.restmoneytransferservice;

import io.github.ilyabystrov.demo.restmoneytransferservice.domain.Account;
import io.github.ilyabystrov.demo.restmoneytransferservice.resource.TransferRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import static javax.ws.rs.client.Entity.entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.test.JerseyTest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static javax.ws.rs.client.Entity.entity;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Data imported from {@code import.sql}.
 */
public class AppApiTest extends JerseyTest {
  
  @Override
  protected Application configure() {
    return App.createResourceConfig();
  }
  
  @Test
  public void accountsExist() {

    Account account1 = target("account/1").request().get(Account.class);
    assertThat(account1, is(new Account(1l, new BigDecimal("100000"))));

    Account account2 = target("account/2").request().get(Account.class);
    assertThat(account2, is(new Account(2l, new BigDecimal("200000"))));

    Account account3 = target("account/3").request().get(Account.class);
    assertThat(account3, is(new Account(3l, new BigDecimal("300000"))));
  }

  @Test
  public void accountDoesntExist() {

    Response response = target("account/100").request().get();
    assertThat(response.getStatus(), is(Response.Status.NOT_FOUND.getStatusCode()));
  }

  @Test
  public void oneTimeTrasfer() {
        
    Response response = target("account/transfer").request()
        .post(entity(new TransferRequest(1l, 2l, new BigDecimal("5.55")), MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(Response.Status.NO_CONTENT.getStatusCode()));
    assertThat(target("account/1").request().get(Account.class), is(new Account(1l, new BigDecimal("99994.45"))));
    assertThat(target("account/2").request().get(Account.class), is(new Account(2l, new BigDecimal("200005.55"))));
  }

  @Test
  public void noMoneyForTransfer() {
        
    Response response = target("account/transfer").request()
        .post(entity(new TransferRequest(1l, 2l, new BigDecimal("100000.01")), MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(Response.Status.CONFLICT.getStatusCode()));
  }

  @Test
  public void oneAccountIsUsedAsSenderAndAsRecipientForTransfer() {
        
    Response response = target("account/transfer").request()
        .post(entity(new TransferRequest(1l, 1l, new BigDecimal("0.01")), MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(Response.Status.CONFLICT.getStatusCode()));
  }

  @Test
  public void multithreadedTransfer() throws InterruptedException {
        
    final CountDownLatch startGate = new CountDownLatch(1);
    final CountDownLatch endGate = new CountDownLatch(5);

    new Thread(new FutureTask<>(() -> {
      startGate.await();
      for(int i = 0; i < 1000; i++) {
        target("account/transfer").request()
            .post(entity(new TransferRequest(1l, 3l, new BigDecimal("1")), MediaType.APPLICATION_JSON));
      }
      endGate.countDown();
      return null;
    })).start();

    new Thread(new FutureTask<>(() -> {
      startGate.await();
      for(int i = 0; i < 1000; i++) {
        target("account/transfer").request()
            .post(entity(new TransferRequest(2l, 3l, new BigDecimal("1")), MediaType.APPLICATION_JSON));
      }
      endGate.countDown();
      return null;
    })).start();

    new Thread(new FutureTask<>(() -> {
      startGate.await();
      for(int i = 0; i < 900; i++) {
        target("account/transfer").request()
            .post(entity(new TransferRequest(3l, 1l, new BigDecimal("1")), MediaType.APPLICATION_JSON));
      }
      endGate.countDown();
      return null;
    })).start();

    new Thread(new FutureTask<>(() -> {
      startGate.await();
      for(int i = 0; i < 900; i++) {
        target("account/transfer").request()
            .post(entity(new TransferRequest(3l, 2l, new BigDecimal("1")), MediaType.APPLICATION_JSON));
      }
      endGate.countDown();
      return null;
    })).start();

    new Thread(new FutureTask<>(() -> {
      startGate.await();
      for(int i = 0; i < 800; i++) {
        target("account/transfer").request()
            .post(entity(new TransferRequest(2l, 1l, new BigDecimal("1")), MediaType.APPLICATION_JSON));
      }
      endGate.countDown();
      return null;
    })).start();


    endGate.await();

    // 1->3: 1000
    // 2->3: 1000
    // 3->1:  900
    // 3->2:  900
    // 2->1:  800

    assertThat(target("account/1").request().get(Account.class), is(new Account(1l, new BigDecimal("100700"))));
    assertThat(target("account/2").request().get(Account.class), is(new Account(2l, new BigDecimal("199100"))));
    assertThat(target("account/3").request().get(Account.class), is(new Account(3l, new BigDecimal("300200"))));
  }

}
