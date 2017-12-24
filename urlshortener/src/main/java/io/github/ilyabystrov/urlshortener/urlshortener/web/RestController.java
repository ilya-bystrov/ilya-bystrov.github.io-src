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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

import static io.github.ilyabystrov.urlshortener.urlshortener.util.PasswordGenerator.generatePassword;

@org.springframework.web.bind.annotation.RestController
public class RestController {

  private static final String ACCOUNT_ID_FIELD = "AccountId";
  private static final String URL_FIELD = "url";

  @Autowired
  private AccountService accountService;

  @RequestMapping(
      value = "/account",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createAccount(@RequestBody Map<String, Object> payload) {

    if (!(payload.get(ACCOUNT_ID_FIELD) instanceof String)) {
      return ResponseEntity.badRequest().body(new Response(false, "String field '" + ACCOUNT_ID_FIELD + "' is expected"));
    }

    String id = (String) payload.get(ACCOUNT_ID_FIELD);

    if (accountService.findById(id).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response(false, "Account with id '" + id + "' already exists"));
    } else {
      Account account = accountService.save(new Account(id, generatePassword()));
      return ResponseEntity.ok(new Response( true, "Account with id '" + account.getId() + "' was created", Optional.of(account.getPassword())));
    }
  }

  @RequestMapping(value = "/register/",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> register(@RequestBody Map<String, Object> payload) {

    if (!(payload.get(URL_FIELD) instanceof String)) {
      return ResponseEntity.badRequest().body(new Response(false, "String field '" + URL_FIELD + "' is expected"));
    }

    String urlString = (String) payload.get(URL_FIELD);
    try {
      URL url = new URL(urlString);
    } catch (MalformedURLException e) {
      return ResponseEntity.badRequest().body(new Response(false, "'" + urlString + " is invalid url"));
    }


    String urlAsString = payload.get(URL_FIELD).toString();
//    return new Account(accountId, generatePassword());
    return null;
  }

  @RequestMapping(value = "/statistic/{accountId}", method = RequestMethod.GET)
  public Account getAccount(@PathVariable String accountId) {
    return new Account(accountId, generatePassword());
  }
}
