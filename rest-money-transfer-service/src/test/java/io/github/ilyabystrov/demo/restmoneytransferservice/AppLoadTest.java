package io.github.ilyabystrov.demo.restmoneytransferservice;

import io.github.ilyabystrov.demo.restmoneytransferservice.domain.Account;
import io.github.ilyabystrov.demo.restmoneytransferservice.resource.TransferRequest;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import static javax.ws.rs.client.Entity.entity;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Data imported from {@code import.sql}.
 */
public class AppLoadTest extends JerseyTest {
  
  @Override
  protected Application configure() {
    return App.createResourceConfig();
  }
  
  @Test
  public void multithreadedTransfer() throws InterruptedException {
        
    final CountDownLatch startGate = new CountDownLatch(1);
    final CountDownLatch endGate = new CountDownLatch(5);

    new Thread(new FutureTask<>(() -> {
      startGate.await();
      for(int i = 1; i <= 1000; i++) {
        target("account/transfer").request()
            .post(entity(new TransferRequest(1l, 3l, new BigDecimal("1")), MediaType.APPLICATION_JSON));
        if(i % 100 == 0) {
          System.out.println("Thread1 send 100 requests");
        }
      }
      endGate.countDown();
      return null;
    })).start();

    new Thread(new FutureTask<>(() -> {
      startGate.await();
      for(int i = 1; i <= 1000; i++) {
        target("account/transfer").request()
            .post(entity(new TransferRequest(2l, 3l, new BigDecimal("1")), MediaType.APPLICATION_JSON));
        if(i % 100 == 0) {
          System.out.println("Thread2 send 100 requests");
        }
      }
      endGate.countDown();
      return null;
    })).start();

    new Thread(new FutureTask<>(() -> {
      startGate.await();
      for(int i = 1; i <= 900; i++) {
        target("account/transfer").request()
            .post(entity(new TransferRequest(3l, 1l, new BigDecimal("1")), MediaType.APPLICATION_JSON));
        if(i % 100 == 0) {
          System.out.println("Thread3 send 100 requests");
        }
      }
      endGate.countDown();
      return null;
    })).start();

    new Thread(new FutureTask<>(() -> {
      startGate.await();
      for(int i = 1; i <= 900; i++) {
        target("account/transfer").request()
            .post(entity(new TransferRequest(3l, 2l, new BigDecimal("1")), MediaType.APPLICATION_JSON));
        if(i % 100 == 0) {
          System.out.println("Thread4 send 100 requests");
        }
      }
      endGate.countDown();
      return null;
    })).start();

    new Thread(new FutureTask<>(() -> {
      startGate.await();
      for(int i = 1; i <= 800; i++) {
        target("account/transfer").request()
            .post(entity(new TransferRequest(2l, 1l, new BigDecimal("1")), MediaType.APPLICATION_JSON));
        if(i % 100 == 0) {
          System.out.println("Thread5 send 100 requests");
        }
      }
      endGate.countDown();
      return null;
    })).start();

    startGate.countDown();
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
