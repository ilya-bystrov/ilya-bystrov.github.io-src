package io.github.ilyabystrov.urlshortener.urlshortener.service;

import io.github.ilyabystrov.urlshortener.urlshortener.domain.entity.Account;
import io.github.ilyabystrov.urlshortener.urlshortener.domain.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@org.springframework.stereotype.Service
public class AccountService implements UserDetailsService {

  @Autowired
  private AccountRepository accountRepository;

  public Optional<Account> findById(String id) {
    return accountRepository.findById(id);
  }

  public Account save(Account entity) {
    return accountRepository.save(entity);
  }

  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

    return findById(id)
        .map(account -> User.withUsername(account.getId()).password(account.getPassword()).roles().build())
        .orElseThrow(() -> new UsernameNotFoundException("User '" + id + "' not found"));
  }
}
