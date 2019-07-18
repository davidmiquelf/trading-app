package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

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
public class Quote implements Entity<String> {

  @JsonProperty("ticker")
  private String ticker;
  @JsonProperty("lastPrice")
  private Double lastPrice = Double.valueOf(0);
  @JsonProperty("bidPrice")
  private Double bidPrice = Double.valueOf(0);
  @JsonProperty("bidSize")
  private Long bidSize = Long.valueOf(0);
  @JsonProperty("askPrice")
  private Double askPrice = Double.valueOf(0);
  @JsonProperty("askSize")
  private Long askSize = Long.valueOf(0);
  @JsonProperty("id")
  private String id;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("ticker")
  public String getTicker() {
    return ticker;
  }

  @JsonProperty("ticker")
  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  @JsonProperty("lastPrice")
  public Double getLastPrice() {
    return lastPrice;
  }

  @JsonProperty("lastPrice")
  public void setLastPrice(Double lastPrice) {
    this.lastPrice = lastPrice;
  }

  @JsonProperty("bidPrice")
  public Double getBidPrice() {
    return bidPrice;
  }

  @JsonProperty("bidPrice")
  public void setBidPrice(Double bidPrice) {
    this.bidPrice = bidPrice;
  }

  @JsonProperty("bidSize")
  public Long getBidSize() {
    return bidSize;
  }

  @JsonProperty("bidSize")
  public void setBidSize(Long bidSize) {
    this.bidSize = bidSize;
  }

  @JsonProperty("askPrice")
  public Double getAskPrice() {
    return askPrice;
  }

  @JsonProperty("askPrice")
  public void setAskPrice(Double askPrice) {
    this.askPrice = askPrice;
  }

  @JsonProperty("askSize")
  public Long getAskSize() {
    return askSize;
  }

  @JsonProperty("askSize")
  public void setAskSize(Long askSize) {
    this.askSize = askSize;
  }

  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(String id) {
    this.id = id;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}