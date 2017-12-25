package io.github.ilyabystrov.urlshortener.urlshortener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
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

  @TestConfiguration
  static class Config {

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
      return new RestTemplateBuilder();
    }
  }

  @Autowired
  private TestRestTemplate restTemplate;

  private ThreadLocal<String> testAccountXPassword;

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
  public void createRegisterRedirectAndShowStatistic() {

    createAccountX();

    register302UrlForAccountX();
    redirect302ForAccountX();

    register301UrlForAccountX();
    redirect301ForAccountX();
    redirect301ForAccountX();

    statisticForAccountX();
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

    String testUrl = "https://www.google.com/search?q=test";

    Map<String, Object> body = new HashMap<>();
    body.put("url", testUrl);
    body.put("redirectType", 301);

    ResponseEntity<Map> register = restTemplate.withBasicAuth("testAccountX", testAccountXPassword.get())
        .exchange("/config/register", HttpMethod.POST, new HttpEntity<>(body), Map.class);
    assertThat(register.getStatusCode(), is(HttpStatus.OK));
    assertThat(register.getBody().get("shortUrl"), is("http://localhost:8080/aA5"));

  }

  private void redirect301ForAccountX() {

    String testUrl = "https://www.google.com/search?q=test";
    ResponseEntity<String> redirect = restTemplate.exchange("http://localhost:8080/aA5", HttpMethod.GET, HttpEntity.EMPTY, String.class);
    assertThat(redirect.getStatusCode(), is(HttpStatus.MOVED_PERMANENTLY));
    assertThat(redirect.getHeaders().getFirst("Location"), is(testUrl));
  }

  private void statisticForAccountX() {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    ResponseEntity<Map> statistic = restTemplate.withBasicAuth("testAccountX", testAccountXPassword.get())
        .exchange("/statistic/testAccountX", HttpMethod.GET, new HttpEntity<>(null, headers), Map.class);

    assertThat(statistic.getStatusCode(), is(HttpStatus.OK));

    Map<String, Integer> expected = new HashMap<>();
    expected.put("http://google.com", 1);
    expected.put("https://www.google.com/search?q=test", 2);

    System.out.println("redirect.getBody() = " + statistic.getBody());
    assertThat(statistic.getBody(), is(expected));
  }
}
