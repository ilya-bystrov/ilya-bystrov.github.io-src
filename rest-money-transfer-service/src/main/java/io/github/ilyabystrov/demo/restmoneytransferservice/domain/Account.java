package io.github.ilyabystrov.demo.restmoneytransferservice.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

  @Id
  @Column
  private Long id;

  @Column
  private BigDecimal balance;

  public Long getId() {
    return id;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  @Override
  public String toString() {
    return "Account{" + "id=" + id + ", balance=" + balance + '}';
  }
}
