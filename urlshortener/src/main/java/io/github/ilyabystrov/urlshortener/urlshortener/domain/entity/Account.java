package io.github.ilyabystrov.urlshortener.urlshortener.domain.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

  @Id
  @Column(name = "account_id")
  private String id;

  public Account(String id) {
    this.id = id;
  }

  Account() {
  }

  public String getId() {
    return id;
  }
}
