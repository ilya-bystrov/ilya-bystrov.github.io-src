package io.github.ilyabystrov.urlshortener.urlshortener.web;

import io.github.ilyabystrov.urlshortener.urlshortener.domain.entity.Account;
import io.github.ilyabystrov.urlshortener.urlshortener.domain.entity.Link;
import io.github.ilyabystrov.urlshortener.urlshortener.service.AccountService;
import io.github.ilyabystrov.urlshortener.urlshortener.service.LinkService;
import io.github.ilyabystrov.urlshortener.urlshortener.web.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static io.github.ilyabystrov.urlshortener.urlshortener.util.Utils.PasswordGenerator.generatePassword;


@RestController
@RequestMapping(
    value = "/config",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class ConfigController {

  private static final String ACCOUNT_ID_FIELD = "accountId";
  private static final String URL_FIELD = "url";
  private static final String SHORT_URL_FIELD = "shortUrl";
  private static final String REDIRECT_TYPE_FIELD = "redirectType";

  @Autowired
  private AccountService accountService;

  @Autowired
  private LinkService linkService;

  @Value("#{hostAndPort}")
  private Map<String, String> hostAndPort;

  @RequestMapping(value = "/account", method = RequestMethod.POST)
  public ResponseEntity<?> createAccount(@RequestBody Map<String, Object> payload) {

    if (!(payload.get(ACCOUNT_ID_FIELD) instanceof String)) {
      return ResponseEntity.badRequest().body(new Response(false, "String field '" + ACCOUNT_ID_FIELD + "' is expected"));
    }

    String id = (String) payload.get(ACCOUNT_ID_FIELD);

    if (accountService.findById(id).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response(false, "Account already exists"));
    } else {
      Account account = accountService.save(new Account(id, generatePassword()));
      return ResponseEntity.ok(new Response(true, "Account was created", Optional.of(account.getPassword())));
    }
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity<?> register(@RequestBody Map<String, Object> payload, Principal principal) {

    if (!(payload.get(URL_FIELD) instanceof String)) {
      return ResponseEntity.badRequest().body(new Response(false, "String field '" + URL_FIELD + "' is expected"));
    }

    if (payload.containsKey(REDIRECT_TYPE_FIELD) && !(payload.get(REDIRECT_TYPE_FIELD) instanceof Integer)) {
      return ResponseEntity.badRequest().body(new Response(false, "Field '" + REDIRECT_TYPE_FIELD + "' value is integer"));
    }

    int redirectType = (int) payload.getOrDefault(REDIRECT_TYPE_FIELD, HttpStatus.MOVED_TEMPORARILY.value());

    if(redirectType != 301 && redirectType != 302) {
      return ResponseEntity.badRequest().body(new Response(false, "Use 301 or 302 codes for redirect"));
    }

    String urlString = (String) payload.get(URL_FIELD);
    URL url;
    try {
      url = new URL(urlString);
    } catch (MalformedURLException e) {
      return ResponseEntity.badRequest().body(new Response(false, "'" + urlString + " is invalid url"));
    }

    String accountId = principal.getName();

    return linkService.findByUrlAndAccount_Id(url, accountId)
        .map(link ->
            (ResponseEntity) ResponseEntity.status(HttpStatus.CONFLICT).body(new Response(false, "Link has already been registered")))
        .orElseGet(() ->
            {
              Link link = linkService.save(new Link(url, redirectType, accountService.findById(accountId).get()));
              Map<String, Object> body = new LinkedHashMap<>();
              body.put(SHORT_URL_FIELD, link.getShortUrl(hostAndPort.get("host"), hostAndPort.get("port")));
              body.put(REDIRECT_TYPE_FIELD, link.getRedirectType());
              return ResponseEntity.ok(body);
            }
        );
  }
}
