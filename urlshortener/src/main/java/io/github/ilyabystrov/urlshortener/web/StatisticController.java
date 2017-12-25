package io.github.ilyabystrov.urlshortener.web;

import io.github.ilyabystrov.urlshortener.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@RestController
@RequestMapping(
    value = "/statistic",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticController {

  @Autowired
  private AccountService accountService;

  @RequestMapping(value = "{accountId}", method = RequestMethod.GET)
  public Map<String, Long> statistic(@PathVariable String accountId, Principal principal) {

    if(!accountId.equals(principal.getName()))  {
      throw new IllegalArgumentException(); // just exception for simplicity
    }

    Map<String, Long> collect = accountService.getStatistic(accountId).entrySet().stream()
        .map(urlLongEntry -> new SimpleImmutableEntry<>(urlLongEntry.getKey().toString(), urlLongEntry.getValue()))
        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    return collect;
  }
}
