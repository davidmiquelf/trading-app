package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "amount",
    "id",
    "traderId"
})
public class Account implements Entity<Long> {

  @JsonProperty("amount")
  private Double amount;
  @JsonProperty("id")
  private Long id;
  @JsonProperty("traderId")
  private Long traderId;

  @JsonProperty("amount")
  public Double getAmount() {
    return amount;
  }

  @JsonProperty("amount")
  public void setAmount(Double amount) {
    this.amount = amount;
  }

  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(Long id) {
    this.id = id;
  }

  @JsonProperty("traderId")
  public Long getTraderId() {
    return traderId;
  }

  @JsonProperty("traderId")
  public void setTraderId(Long traderId) {
    this.traderId = traderId;
  }

}
