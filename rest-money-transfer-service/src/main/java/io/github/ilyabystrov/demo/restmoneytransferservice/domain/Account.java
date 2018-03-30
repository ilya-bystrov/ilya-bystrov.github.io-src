package io.github.ilyabystrov.demo.restmoneytransferservice.domain;

import java.math.BigDecimal;
import java.util.Objects;
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

  // for Hibernate
  public Account() {
  }

  public Account(Long id, BigDecimal balance) {
    this.id = id;
    this.balance = balance;
  }

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
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Account)) {
      return false;
    }
    final Account other = (Account) obj;
    return Objects.equals(this.id, other.id) && Objects.nonNull(this.balance) && (this.balance.compareTo(other.balance) == 0);
  }

  @Override
  public String toString() {
    return "Account{" + "id=" + id + ", balance=" + balance + '}';
  }
}
