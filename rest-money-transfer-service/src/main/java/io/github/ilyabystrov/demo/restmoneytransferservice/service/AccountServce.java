package io.github.ilyabystrov.demo.restmoneytransferservice.service;

import io.github.ilyabystrov.demo.restmoneytransferservice.domain.Account;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import javax.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.testing.transaction.TransactionUtil;

public class AccountServce {
  
  private final ConcurrentHashMap<Long, Object> locks = new ConcurrentHashMap<>();
  
  @Inject
  private SessionFactory sessionFactory;
  
  public AccountServce() {
  }
  
  public void transferMoney(Long senderId, Long recipientId, BigDecimal amount) {
    
    if(Objects.equals(senderId, recipientId)) {
      throw TransferException.sameAccountTransferException(senderId, amount);
    }
    
    locks.putIfAbsent(senderId, new Object());
    locks.putIfAbsent(recipientId, new Object());
    
    Object formerLock = senderId < recipientId ? locks.get(senderId) : locks.get(recipientId);
    Object laterLock  = senderId > recipientId ? locks.get(senderId) : locks.get(recipientId);
    
    synchronized(formerLock) {
      synchronized(laterLock) {
        
        TransactionUtil.doInHibernate(() -> sessionFactory, session -> {
          transferMoneyHelper(getAccountHelper(senderId).apply(session), getAccountHelper(recipientId).apply(session), amount);
        });
      }
    }
  }
  
  private void transferMoneyHelper(Account sender, Account recipient, BigDecimal amount) {
    
    if (sender.getBalance().compareTo(amount) < 0) {
      throw TransferException.noMoneyForTransferException(sender, recipient, amount);
    }
    sender.setBalance(sender.getBalance().subtract(amount));
    recipient.setBalance(recipient.getBalance().add(amount));
  }
  
  private Function<Session, Account> getAccountHelper(Long accountId) {
    return (Session session) ->
        session.byId(Account.class).loadOptional(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));
  }
  
  public Account getAccount(Long accountId){
    return TransactionUtil.doInHibernate(() -> sessionFactory, getAccountHelper(accountId)::apply);
  }
}
