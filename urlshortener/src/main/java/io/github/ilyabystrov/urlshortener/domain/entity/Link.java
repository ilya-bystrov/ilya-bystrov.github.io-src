package io.github.ilyabystrov.urlshortener.domain.entity;

import io.github.ilyabystrov.urlshortener.util.Utils.SimpleBase62Encoder;
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
  private Integer redirectType;

  @Column(name = "visited", columnDefinition = "bigint default 0 not null")
  private Long visited = 0l;

  @ManyToOne
  @JoinColumn(name="account_id")
  private Account account;

  public Link(URL url, int redirectType, Account account) {
    this.url = url;
    this.redirectType = redirectType;
    this.account = account;
  }

  Link() { // for Hibernate
  }

  public Long getId() {
    return id;
  }

  public URL getUrl() {
    return url;
  }

  public URL getShortUrl(String host, String port) {
    try {
      // Link depends on springframework (UriComponentsBuilder), for simplicity
      return UriComponentsBuilder.newInstance()
          .scheme("http").host(host).port(port)
          .path(SimpleBase62Encoder.encode(getId()))
          .build().toUri().toURL();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  public Integer getRedirectType() {
    return redirectType;
  }

  public Account getAccount() {
    return account;
  }

  public Long getVisited() {
    return visited;
  }
}
