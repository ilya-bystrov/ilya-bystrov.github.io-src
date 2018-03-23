package io.github.ilyabystrov.demo.restmoneytransferservice.service;

import io.github.ilyabystrov.demo.restmoneytransferservice.domain.Account;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import org.hibernate.SessionFactory;
import org.hibernate.testing.transaction.TransactionUtil;

/**
 *
 * @author Ilya Bystrov @iliabystrov
 */
public class TransferManager {
  
  private final ConcurrentHashMap<Long, Object> locks = new ConcurrentHashMap<>();
  private final SessionFactory sessionFactory;

  public TransferManager(SessionFactory factory) {
    this.sessionFactory = factory;
  }
  
  public void transferMoney(Long senderId, Long recipientId, BigDecimal amount) {
    TransactionUtil.doInHibernate(() -> sessionFactory, session -> {
      transferMoneyHelper(session.get(Account.class, senderId), session.get(Account.class, recipientId), amount);
    });
  }

  protected void transferMoneyHelper(Account sender, Account recipient, BigDecimal amount) {
    
    locks.putIfAbsent(sender.getId(), new Object());
    locks.putIfAbsent(recipient.getId(), new Object());
    
    Object formerLock = sender.getId() < recipient.getId() ? locks.get(sender.getId()) : locks.get(recipient.getId());
    Object laterLock  = sender.getId() > recipient.getId() ? locks.get(sender.getId()) : locks.get(recipient.getId());
    
    synchronized(formerLock) {
      synchronized(laterLock) {

        if (sender.getBalance().compareTo(amount) < 0) {
          throw new IllegalArgumentException( "Transfer cannot be completed");
        }
        sender.setBalance(sender.getBalance().subtract(amount));
        recipient.setBalance(sender.getBalance().add(amount));
      }
    }
  }
}
