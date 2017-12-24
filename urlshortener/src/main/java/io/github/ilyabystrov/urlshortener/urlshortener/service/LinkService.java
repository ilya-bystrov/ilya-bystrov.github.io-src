package io.github.ilyabystrov.urlshortener.urlshortener.service;

import io.github.ilyabystrov.urlshortener.urlshortener.domain.LinkRepository;
import io.github.ilyabystrov.urlshortener.urlshortener.domain.entity.Link;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.Optional;

import static io.github.ilyabystrov.urlshortener.urlshortener.util.Utils.SimpleBase62Encoder.decode;

@org.springframework.stereotype.Service
public class LinkService {

  @Autowired
  private LinkRepository linkRepository;

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
}
