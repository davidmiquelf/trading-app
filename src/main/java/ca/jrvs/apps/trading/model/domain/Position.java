package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "accountId",
    "position",
    "ticker"
})
public class Position {

  @JsonProperty("accountId")
  private Long accountId;
  @JsonProperty("position")
  private Long position;
  @JsonProperty("ticker")
  private String ticker;

  @JsonProperty("accountId")
  public Long getAccountId() {
    return accountId;
  }

  @JsonProperty("accountId")
  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  @JsonProperty("position")
  public Long getPosition() {
    return position;
  }

  @JsonProperty("position")
  public void setPosition(Long position) {
    this.position = position;
  }

  @JsonProperty("ticker")
  public String getTicker() {
    return ticker;
  }

  @JsonProperty("ticker")
  public void setTicker(String ticker) {
    this.ticker = ticker;
  }
}
