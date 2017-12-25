package io.github.ilyabystrov.urlshortener.urlshortener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class ApplicationTests {

//  private TestRestTemplate restTemplate = new TestRestTemplate();

  @TestConfiguration
  static class Config {

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
      return new RestTemplateBuilder();
//          .additionalMessageConverters(...)
//                .customizers(...);
    }

  }


  @Autowired
  private TestRestTemplate restTemplate;

  private ThreadLocal<String> testAccountXPassword;
  private ThreadLocal<String> testAccountYPassword;


  @Test
  public void contextLoads() {
  }

  public void createAccountX() {

    ResponseEntity<Map> response = restTemplate.exchange("/config/account", HttpMethod.POST, new HttpEntity<>(singletonMap("accountId", "testAccountX")), Map.class);
    assertThat(HttpStatus.OK, is(response.getStatusCode()));
    assertThat(response.getBody().containsKey("password"), is(true));
    testAccountXPassword = ThreadLocal.withInitial(() -> (String) response.getBody().get("password"));
  }

  @Test
  public void accountXAlreadyExists() {

    createAccountX();

    ResponseEntity<Map> response = restTemplate.exchange("/config/account", HttpMethod.POST, new HttpEntity<>(singletonMap("accountId", "testAccountX")), Map.class);
    assertThat(HttpStatus.CONFLICT, is(response.getStatusCode()));
  }

  @Test
  public void registerUrlUsingAccountXAndRedirect302() {

    createAccountX();

    register302UrlForAccountX();
    redirect302ForAccountX();

    register301UrlForAccountX();
    redirect301ForAccountX();
  }

  private void register302UrlForAccountX() {

    String testUrl = "http://google.com";
    ResponseEntity<Map> register = restTemplate.withBasicAuth("testAccountX", testAccountXPassword.get())
        .exchange("/config/register", HttpMethod.POST, new HttpEntity<>(singletonMap("url", testUrl)), Map.class);
    assertThat(register.getStatusCode(), is(HttpStatus.OK));
    assertThat(register.getBody().get("shortUrl"), is("http://localhost:8080/aA4"));
  }

  private void redirect302ForAccountX() {

    String testUrl = "http://google.com";
    ResponseEntity<String> redirect = restTemplate.exchange("http://localhost:8080/aA4", HttpMethod.GET, HttpEntity.EMPTY, String.class);
    assertThat(redirect.getStatusCode().value(), is(HttpStatus.MOVED_TEMPORARILY.value())); // value() used because of multiple enums with same code
    assertThat(redirect.getHeaders().getFirst("Location"), is(testUrl));
  }

  private void register301UrlForAccountX() {

    String testUrl = "https://www.google.ru/search?q=test";

    Map<String, Object> body = new HashMap<>();
    body.put("url", testUrl);
    body.put("redirectType", 301);

    ResponseEntity<Map> register = restTemplate.withBasicAuth("testAccountX", testAccountXPassword.get())
        .exchange("/config/register", HttpMethod.POST, new HttpEntity<>(body), Map.class);
    assertThat(register.getStatusCode(), is(HttpStatus.OK));
    assertThat(register.getBody().get("shortUrl"), is("http://localhost:8080/aA5"));

  }

  private void redirect301ForAccountX() {

    String testUrl = "https://www.google.ru/search?q=test";
    ResponseEntity<String> redirect = restTemplate.exchange("http://localhost:8080/aA5", HttpMethod.GET, HttpEntity.EMPTY, String.class);
    assertThat(redirect.getStatusCode(), is(HttpStatus.MOVED_PERMANENTLY));
    assertThat(redirect.getHeaders().getFirst("Location"), is(testUrl));
  }
}
