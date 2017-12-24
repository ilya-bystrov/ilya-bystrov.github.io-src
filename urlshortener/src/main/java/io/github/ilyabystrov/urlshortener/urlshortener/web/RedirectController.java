package io.github.ilyabystrov.urlshortener.urlshortener.web;

import io.github.ilyabystrov.urlshortener.urlshortener.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class RedirectController {

  @Autowired
  private LinkService linkService;

  @RequestMapping(value = "{shortUrlPath}", method = RequestMethod.GET)
  public ResponseEntity<?> createAccount(@PathVariable String shortUrlPath) {

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(URI.create(linkService.findByShortUrl(shortUrlPath).get().getUrl().toString()));
    return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);
  }
}
