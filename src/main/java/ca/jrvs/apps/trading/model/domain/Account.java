package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "traderId",
        "amount"
})
public class Account implements Entity<Long>{

  @JsonProperty("id")
  private Long id;
  @JsonProperty("traderId")
  private Long traderId;
  @JsonProperty("amount")
  private Double amount;

  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(Long id) {
    this.id = id;
  }

  public Account withId(Long id) {
    this.id = id;
    return this;
  }

  @JsonProperty("traderId")
  public Long getTraderId() {
    return traderId;
  }

  @JsonProperty("traderId")
  public void setTraderId(Long traderId) {
    this.traderId = traderId;
  }

  public Account withTraderId(Long traderId) {
    this.traderId = traderId;
    return this;
  }

  @JsonProperty("amount")
  public Double getAmount() {
    return amount;
  }

  @JsonProperty("amount")
  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Account withAmount(Double amount) {
    this.amount = amount;
    return this;
  }

}