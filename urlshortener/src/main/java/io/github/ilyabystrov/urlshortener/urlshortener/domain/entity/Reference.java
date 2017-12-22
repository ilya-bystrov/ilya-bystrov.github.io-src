package io.github.ilyabystrov.urlshortener.urlshortener.domain.entity;

import javax.persistence.*;
import java.net.URL;

@Entity
public class Reference {

  @Id
  @Column(name = "link_id")
  private Long id;

  @Column(name = "url")
  private URL url;

  @Column(name = "short_url")
  private URL shortUrl;

  @Column(name = "redirect_type")
  private String redirectType;

  @ManyToOne
  @JoinColumn(name="account_id")
  private Account account;

}
