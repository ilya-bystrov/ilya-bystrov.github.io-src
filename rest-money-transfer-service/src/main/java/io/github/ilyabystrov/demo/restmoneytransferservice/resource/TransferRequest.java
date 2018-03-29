package io.github.ilyabystrov.demo.restmoneytransferservice.resource;

import java.math.BigDecimal;

public class TransferRequest {

  private Long sender;
  private Long recipient;
  private BigDecimal amount;

  public Long getSender() {
    return sender;
  }

  public void setSender(Long sender) {
    this.sender = sender;
  }

  public Long getRecipient() {
    return recipient;
  }

  public void setRecipient(Long recipient) {
    this.recipient = recipient;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "TransferRequest{" + "sender=" + sender + ", recipient=" + recipient + ", amount=" + amount + '}';
  }
}
