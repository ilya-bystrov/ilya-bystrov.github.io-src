package io.github.ilyabystrov.urlshortener.web;

import io.github.ilyabystrov.urlshortener.service.LinkService;
import io.github.ilyabystrov.urlshortener.web.model.Response;
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
  public ResponseEntity<?> redirect(@PathVariable String shortUrlPath) {

    return linkService.findByShortUrl(shortUrlPath)
        .map(link -> {
          linkService.incrementVisited(link.getId());
          HttpHeaders headers = new HttpHeaders();
          headers.setLocation(URI.create(link.getUrl().toString()));
          return new ResponseEntity<>(headers, HttpStatus.valueOf(link.getRedirectType()));
        }).orElse(ResponseEntity.ok(new Response(false, "Link is not registered")));
  }
}
