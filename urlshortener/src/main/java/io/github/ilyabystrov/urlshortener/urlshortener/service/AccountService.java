package io.github.ilyabystrov.urlshortener.urlshortener.service;

import io.github.ilyabystrov.urlshortener.urlshortener.domain.entity.Account;
import io.github.ilyabystrov.urlshortener.urlshortener.domain.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@org.springframework.stereotype.Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;

  public Optional<Account> findById(String id) {
    return accountRepository.findById(id);
  }

  public Account save(Account entity) {
    return accountRepository.save(entity);
  }
}
