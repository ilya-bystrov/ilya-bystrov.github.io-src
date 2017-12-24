package io.github.ilyabystrov.urlshortener.urlshortener.service;

import io.github.ilyabystrov.urlshortener.urlshortener.domain.LinkRepository;
import io.github.ilyabystrov.urlshortener.urlshortener.domain.entity.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.github.ilyabystrov.urlshortener.urlshortener.util.Utils.SimpleBase62Encoder.decode;

@org.springframework.stereotype.Service
public class LinkService {

  @Autowired
  private LinkRepository linkRepository;

  @Autowired
  private NamedParameterJdbcTemplate jdbc;

  public Link save(Link entity) {
    return linkRepository.save(entity);
  }

  public Optional<Link> findById(Long id) {
    return linkRepository.findById(id);
  }

  public Optional<Link> findByShortUrl(String shortUrlPath) {
    return linkRepository.findById(decode(shortUrlPath));
  }

  public Optional<Link> findByUrl(URL url) {
    return linkRepository.findByUrl(url);
  }

  public Optional<Link> findByUrlAndAccount_Id(URL url, String accountId) {
    return linkRepository.findByUrlAndAccount_Id(url, accountId);
  }

   // Implemented in lock-free manner
  public void incrementVisited(Long linkId) {
    Map<String, Object> params = new HashMap<>();
    params.put("linkId", linkId);
    int updatedRowsCount;
    do {
      Long prevVisited = jdbc.queryForObject("select visited from link where link_id = :linkId", params, Long.class);
      params.put("prevVisited", prevVisited);
      params.put("visited", prevVisited + 1);
      updatedRowsCount= jdbc.update("update link set visited = :visited where link_id = :linkId and visited = :prevVisited", params);
    } while (updatedRowsCount != 1);
  }
}
