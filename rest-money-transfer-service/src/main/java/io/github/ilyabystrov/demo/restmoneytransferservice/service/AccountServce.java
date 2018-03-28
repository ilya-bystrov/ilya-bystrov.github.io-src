package io.github.ilyabystrov.demo.restmoneytransferservice.service;

import io.github.ilyabystrov.demo.restmoneytransferservice.domain.Account;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.testing.transaction.TransactionUtil;

@ApplicationScoped
public class AccountServce {
  
  private final ConcurrentHashMap<Long, Object> locks = new ConcurrentHashMap<>();
  private StandardServiceRegistry registry;

  @Inject
  private SessionFactory sessionFactory;

  public AccountServce() {
  }


//  @PostConstruct 
//  public void configurePersistence() {
    // in real-life example persistence layer should be started sepe
//    registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
//    Metadata metadata = new MetadataSources(registry).addAnnotatedClass(Account.class).buildMetadata();
//    sessionFactory = metadata.getSessionFactoryBuilder().build();
//  }

//  @PreDestroy
//  public void shutdownPersistence() {
//    StandardServiceRegistryBuilder.destroy(registry);
//  }

//  public AccountServce(SessionFactory factory) {
//    this.sessionFactory = factory;
//  }
  
  public void transferMoney(Long senderId, Long recipientId, BigDecimal amount) {
    TransactionUtil.doInHibernate(() -> sessionFactory, session -> {
      transferMoneyHelper(getAccountHelper(senderId).apply(session), getAccountHelper(recipientId).apply(session), amount);
    });
  }
  
  private void transferMoneyHelper(Account sender, Account recipient, BigDecimal amount) {
    
    locks.putIfAbsent(sender.getId(), new Object());
    locks.putIfAbsent(recipient.getId(), new Object());
    
    Object formerLock = sender.getId() < recipient.getId() ? locks.get(sender.getId()) : locks.get(recipient.getId());
    Object laterLock  = sender.getId() > recipient.getId() ? locks.get(sender.getId()) : locks.get(recipient.getId());
    
    synchronized(formerLock) {
      synchronized(laterLock) {
        
        if (sender.getBalance().compareTo(amount) < 0) {
          throw new IllegalStateException( "Transfer cannot be completed, the sender doesn't contain enough money");
        }
        sender.setBalance(sender.getBalance().subtract(amount));
        recipient.setBalance(recipient.getBalance().add(amount));
      }
    }
  }
  
  private Function<Session, Account> getAccountHelper(Long accountId) {
    return (Session session) ->
        session.byId(Account.class).loadOptional(accountId)
            .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));
  }
  
  public Account getAccount(Long accountId){
    return TransactionUtil.doInHibernate(() -> sessionFactory, getAccountHelper(accountId)::apply);
  }
}
