package io.github.ilyabystrov.urlshortener.web;

import io.github.ilyabystrov.urlshortener.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping(
    value = "/statistic",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticController {

  @Autowired
  private AccountService accountService;

  @RequestMapping(value = "{accountId}", method = RequestMethod.GET)
  public Map<URL, Long> statistic(@PathVariable String accountId, Principal principal) {

    if(!accountId.equals(principal.getName()))  {
      throw new IllegalArgumentException(); // only exception for simplicity
    }

    return accountService.getStatistic(accountId);
  }
}
