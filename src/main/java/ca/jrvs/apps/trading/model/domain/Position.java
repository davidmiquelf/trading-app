package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountId",
        "position",
        "ticker"
})
public class Position {

  @JsonProperty("accountId")
  private Integer accountId;
  @JsonProperty("position")
  private Integer position;
  @JsonProperty("ticker")
  private String ticker;

  @JsonProperty("accountId")
  public Integer getAccountId() {
    return accountId;
  }

  @JsonProperty("accountId")
  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public Position withAccountId(Integer accountId) {
    this.accountId = accountId;
    return this;
  }

  @JsonProperty("position")
  public Integer getPosition() {
    return position;
  }

  @JsonProperty("position")
  public void setPosition(Integer position) {
    this.position = position;
  }

  public Position withPosition(Integer position) {
    this.position = position;
    return this;
  }

  @JsonProperty("ticker")
  public String getTicker() {
    return ticker;
  }

  @JsonProperty("ticker")
  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public Position withTicker(String ticker) {
    this.ticker = ticker;
    return this;
  }

}