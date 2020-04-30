package io.github.ilyabystrov.java.multithreading.bankaccountexample.impl;

import org.junit.Test;

public class NotSafeTransferManagerTest {
  
  @Test
  public void testTransfer() throws InterruptedException {
    new TestDriver(new NotSafeTransferManager()).testTransferManager();
  }
}
