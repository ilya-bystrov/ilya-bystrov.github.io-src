package io.github.ilyabystrov.demo.restmoneytransferservice.service;

import io.github.ilyabystrov.demo.restmoneytransferservice.domain.Account;
import java.math.BigDecimal;

public class TransferException extends RuntimeException {

  private TransferException(String message) {
    super(message);
  }
  
  public static TransferException noMoneyForTransferException(Account sender, Account recipient, BigDecimal amount) {
    return new TransferException("The transfer " + amount + " from " + sender + " to " + recipient + " cannot be performed, the sender doesn't have enough money");
  }

  public static TransferException sameAccountTransferException(Long accountId,BigDecimal amount) {
    return new TransferException("The transfer " + amount + " cannot be performed, the same account " + accountId + " was used for the sender and for the recipient");
  }
}
