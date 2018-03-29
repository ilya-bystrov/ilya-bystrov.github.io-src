package io.github.ilyabystrov.demo.restmoneytransferservice.service;

public class AccountNotFoundException extends RuntimeException {
  
  public AccountNotFoundException(Long accountId) {
    super("Account not found: " + accountId);
  }
}
