package io.github.ilyabystrov.urlshortener.urlshortener.domain.entity;

import io.github.ilyabystrov.urlshortener.urlshortener.util.Utils.SimpleBase62Encoder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import java.net.MalformedURLException;
import java.net.URL;

@Entity
public class Link {

  @Id
  @GeneratedValue
  @Column(name = "link_id")
  private Long id;

  @Column(name = "url")
  private URL url;

  @Column(name = "redirect_type")
  private String redirectType;

  @ManyToOne
  @JoinColumn(name="account_id")
  private Account account;

  public Link(URL url) {
    this.url = url;
  }

  Link() {
  }

  public Long getId() {
    return id;
  }

  public URL getUrl() {
    return url;
  }

  public URL getShortUrl(String host, String port) {
    try {
      // Using only http schema for simplicity
      // Entity contains spring framework dependency for simplicity
      return UriComponentsBuilder.newInstance()
          .scheme("http").host(host).port(port)
          .path(SimpleBase62Encoder.encode(getId()))
          .build().toUri().toURL();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  public String getRedirectType() {
    return redirectType;
  }

  public void setRedirectType(String redirectType) {
    this.redirectType = redirectType;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }
}
