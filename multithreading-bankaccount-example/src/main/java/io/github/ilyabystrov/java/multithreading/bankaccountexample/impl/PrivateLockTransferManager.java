package io.github.ilyabystrov.java.multithreading.bankaccountexample.impl;

import io.github.ilyabystrov.java.multithreading.bankaccountexample.BankAccount;
import io.github.ilyabystrov.java.multithreading.bankaccountexample.TransferManager;

public class PrivateLockTransferManager implements TransferManager {
  
  private final Object lock = new Object();
  
  @Override
  public void transfer(BankAccount fromAccount, BankAccount toAccount, int amount) {
    if (fromAccount.getBalance() < amount) {
      throw new IllegalStateException();
    }
    synchronized(lock){
      fromAccount.withdraw(amount);
      toAccount.deposit(amount);
    }
  }
}
