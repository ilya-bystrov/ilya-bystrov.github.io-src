package io.github.ilyabystrov.urlshortener.urlshortener.web;


import io.github.ilyabystrov.urlshortener.urlshortener.domain.entity.Account;
import io.github.ilyabystrov.urlshortener.urlshortener.service.AccountService;
import io.github.ilyabystrov.urlshortener.urlshortener.web.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@org.springframework.web.bind.annotation.RestController
public class RestController {

  private static final String ACCOUNT_ID = "AccountId";

  @Autowired
  private AccountService accountService;

  @RequestMapping(
      value = "/account",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createAccount(@RequestBody Map<String, Object> payload) {

    if(!(payload.get(ACCOUNT_ID) instanceof String)) {
      return ResponseEntity.badRequest().body(new Response(false, "String field '" + ACCOUNT_ID + "' is expected"));
    }

    String id = (String) payload.get(ACCOUNT_ID);

    if(accountService.findById(id).isPresent()) {
       return ResponseEntity.status(HttpStatus.CONFLICT) .body(new Response(false, String.format("Account with id '%s' already exists", id)));
    } else {
      Account account = accountService.save(new Account(id));
      return ResponseEntity.ok(new Response(true, String.format("Account with id '%s' was created", account.getId())));
    }
  }

  @RequestMapping(value = "/statistic/{accountId}", method = RequestMethod.GET)
  public Account getAccount(@PathVariable String accountId) {
    return new Account(accountId);
  }
}
