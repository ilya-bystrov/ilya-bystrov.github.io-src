package io.github.ilyabystrov.urlshortener.domain;

import io.github.ilyabystrov.urlshortener.domain.entity.Link;
import org.springframework.data.repository.Repository;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public interface LinkRepository extends Repository<Link, Long> {

  Optional<Link> findById(Long id);

  Optional<Link> findByUrl(URL url);

  Optional<Link> findByUrlAndAccount_Id(URL url, String accountId);

  List<Link> findByAccount_Id(String accountId);

  Link save(Link entity);
}
