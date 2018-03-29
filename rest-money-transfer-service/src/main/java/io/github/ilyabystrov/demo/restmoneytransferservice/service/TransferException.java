package io.github.ilyabystrov.demo.restmoneytransferservice.service;

import io.github.ilyabystrov.demo.restmoneytransferservice.domain.Account;
import java.math.BigDecimal;

public class TransferException extends RuntimeException {
  
  public TransferException(Account sender, Account recipient, BigDecimal amount) {
    super("The transfer " + amount + " from " + sender + " to " + recipient + " cannot be performed, the sender doesn't have enough money");
  }
}
