package io.github.ilyabystrov.java.multithreading.bankaccountexample;

public interface TransferManager {
  
  void transfer(BankAccount fromAccount, BankAccount toAccount, int amount);
}
