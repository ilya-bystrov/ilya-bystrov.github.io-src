package io.github.ilyabystrov.urlshortener.urlshortener.domain.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

  @Id
  @Column(name = "account_id")
  private String id;

  @Column(name = "password")
  private String password;

  public Account(String id, String password) {
    this.id = id;
    this.password = password;
  }

  Account() { // for Hibernate
  }

  public String getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }
}
