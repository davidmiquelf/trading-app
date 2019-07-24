package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ticker",
        "lastPrice",
        "bidPrice",
        "bidSize",
        "askPrice",
        "askSize",
        "id"
})
public class Quote implements Entity<String>{

  @JsonProperty("ticker")
  private String ticker;
  @JsonProperty("lastPrice")
  private Double lastPrice;
  @JsonProperty("bidPrice")
  private Double bidPrice;
  @JsonProperty("bidSize")
  private Long bidSize;
  @JsonProperty("askPrice")
  private Double askPrice;
  @JsonProperty("askSize")
  private Long askSize;

  @JsonProperty("ticker")
  public String getTicker() {
    return ticker;
  }

  @JsonProperty("ticker")
  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public Quote withTicker(String ticker) {
    this.ticker = ticker;
    return this;
  }

  @JsonProperty("lastPrice")
  public Double getLastPrice() {
    return lastPrice;
  }

  @JsonProperty("lastPrice")
  public void setLastPrice(Double lastPrice) {
    this.lastPrice = lastPrice;
  }

  public Quote withLastPrice(Double lastPrice) {
    this.lastPrice = lastPrice;
    return this;
  }

  @JsonProperty("bidPrice")
  public Double getBidPrice() {
    return bidPrice;
  }

  @JsonProperty("bidPrice")
  public void setBidPrice(Double bidPrice) {
    this.bidPrice = bidPrice;
  }

  public Quote withBidPrice(Double bidPrice) {
    this.bidPrice = bidPrice;
    return this;
  }

  @JsonProperty("bidSize")
  public Long getBidSize() {
    return bidSize;
  }

  @JsonProperty("bidSize")
  public void setBidSize(Long bidSize) {
    this.bidSize = bidSize;
  }

  public Quote withBidSize(Long bidSize) {
    this.bidSize = bidSize;
    return this;
  }

  @JsonProperty("askPrice")
  public Double getAskPrice() {
    return askPrice;
  }

  @JsonProperty("askPrice")
  public void setAskPrice(Double askPrice) {
    this.askPrice = askPrice;
  }

  public Quote withAskPrice(Double askPrice) {
    this.askPrice = askPrice;
    return this;
  }

  @JsonProperty("askSize")
  public Long getAskSize() {
    return askSize;
  }

  @JsonProperty("askSize")
  public void setAskSize(Long askSize) {
    this.askSize = askSize;
  }

  public Quote withAskSize(Long askSize) {
    this.askSize = askSize;
    return this;
  }

  @JsonProperty("id")
  public String getId() {
    return ticker;
  }

  @JsonProperty("id")
  public void setId(String id) {
    this.ticker = id;
  }

  public Quote withId(String id) {
    this.ticker = id;
    return this;
  }

}