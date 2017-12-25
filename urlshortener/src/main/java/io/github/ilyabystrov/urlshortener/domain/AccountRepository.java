package io.github.ilyabystrov.urlshortener.domain;

import io.github.ilyabystrov.urlshortener.domain.entity.Account;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface AccountRepository extends Repository<Account, String> {

  Optional<Account> findById(String id);

  Account save(Account entity);
}
