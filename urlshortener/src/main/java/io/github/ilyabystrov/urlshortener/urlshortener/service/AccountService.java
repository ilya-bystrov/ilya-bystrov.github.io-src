package io.github.ilyabystrov.urlshortener.urlshortener.service;

import io.github.ilyabystrov.urlshortener.urlshortener.domain.AccountRepository;
import io.github.ilyabystrov.urlshortener.urlshortener.domain.LinkRepository;
import io.github.ilyabystrov.urlshortener.urlshortener.domain.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.net.URL;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class AccountService implements UserDetailsService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private LinkRepository linkRepository;

  @Autowired
  private NamedParameterJdbcTemplate jdbc;

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

  public Map<URL, Long> getStatistic(String accountId) {
   return linkRepository.findByAccount_Id(accountId).stream()
        .map(link -> new SimpleImmutableEntry<>(link.getUrl(), link.getVisited()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }
}
