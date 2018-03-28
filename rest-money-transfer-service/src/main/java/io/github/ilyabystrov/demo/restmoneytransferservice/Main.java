package io.github.ilyabystrov.demo.restmoneytransferservice;

import io.github.ilyabystrov.demo.restmoneytransferservice.domain.Account;
import io.github.ilyabystrov.demo.restmoneytransferservice.service.AccountServce;
import java.math.BigDecimal;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author Ilya Bystrov @iliabystrov
 */
public class Main {
  
  public static void main(String[] args) {
    StandardServiceRegistry registry
        = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
    Metadata metadata = new MetadataSources(registry).addAnnotatedClass(Account.class).buildMetadata();
    SessionFactory factory = metadata.getSessionFactoryBuilder().build();
    final AccountServce manager = new AccountServce();
//    System.out.println(manager.getAccount(1l));
//    System.out.println(manager.getAccount(2l));
//    System.out.println(manager.getAccount(3l));
    manager.transferMoney(1l, 3l, new BigDecimal(10000));
    System.out.println(manager.getAccount(1l));
    System.out.println(manager.getAccount(2l));
    System.out.println(manager.getAccount(3l));
//    manager.transferMoney(3l, 1l, new BigDecimal(10000));
    StandardServiceRegistryBuilder.destroy(registry);
  }
}
