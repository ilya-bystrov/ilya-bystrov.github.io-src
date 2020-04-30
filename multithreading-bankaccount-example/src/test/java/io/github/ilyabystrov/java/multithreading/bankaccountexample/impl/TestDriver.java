package io.github.ilyabystrov.java.multithreading.bankaccountexample.impl;

import io.github.ilyabystrov.java.multithreading.bankaccountexample.BankAccount;
import io.github.ilyabystrov.java.multithreading.bankaccountexample.TransferManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class TestDriver {
  
  private final ExecutorService executorService = Executors.newFixedThreadPool(100);
  
  private final BankAccount oneAccount = new BankAccount(100000);
  private final BankAccount anotherAccount = new BankAccount(100000);
  
  private final TransferManager transferManager;
  
  public TestDriver(TransferManager transferManager) {
    this.transferManager = transferManager;
  }
  
  public void testTransferManager() throws InterruptedException{
    
    List<Integer> transferAmounts = generateTransferAmounts();
    
    List<Callable<Void>> oneToAnotherTxs = new ArrayList<>(2000);
    {
      ListIterator<Integer> forwardIterator = transferAmounts.listIterator();
      while (forwardIterator.hasNext()) {
        Integer amount = forwardIterator.next();
        oneToAnotherTxs.add((Callable<Void>) () -> {
          transferManager.transfer(oneAccount, anotherAccount, amount);
          return null;
        });
      }
    }
    
    List<Callable<Void>> anotherToOneTxs = new ArrayList<>(2000);
    {
      ListIterator<Integer> backwardIterator = transferAmounts.listIterator(transferAmounts.size());
      while (backwardIterator.hasPrevious()) {
        Integer amount = backwardIterator.previous();
        anotherToOneTxs.add((Callable<Void>) () -> {
          transferManager.transfer(anotherAccount, oneAccount, amount);
          return null;
        });
      }
    }
    
    System.out.println("oneAccount.getBalance() = " + oneAccount.getBalance());
    System.out.println("anotherAccount.getBalance() = " + anotherAccount.getBalance());
    
    
    List<Callable<Void>> allTxs = new ArrayList<>(4000);
    {
      allTxs.addAll(oneToAnotherTxs);
      allTxs.addAll(anotherToOneTxs);
      Collections.shuffle(allTxs);
    }

    System.out.println("Transactions started");
    executorService.invokeAll(allTxs);
    executorService.shutdown();
    executorService.awaitTermination(1, TimeUnit.MINUTES);
    System.out.println("Transactions ended");
    
    System.out.println("oneAccount.getBalance() = " + oneAccount.getBalance());
    System.out.println("anotherAccount.getBalance() = " + anotherAccount.getBalance());
  }
  
  private List<Integer> generateTransferAmounts() {
    List<Integer> values = new ArrayList<>(2000);
    int sum = 0;
    while (sum < 99900) {
      int value = ThreadLocalRandom.current().nextInt(0, 100);
      sum += value;
      values.add(value);
    }
    int lastValue = 10000 - sum;
    if(values.size() % 2 == 0) {
      values.add(lastValue);
      values.add(0);
    } else {
      values.add(lastValue);
    }
    sum += lastValue;
    return values;
  }
}
