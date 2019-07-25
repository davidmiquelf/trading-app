package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@JsonPropertyOrder({
    "id",
    "accountId",
    "status",
    "ticker",
    "size",
    "price",
    "notes"
})
public class SecurityOrder implements Entity<Integer> {

  @JsonProperty("id")
  private Integer id;
  @JsonProperty("accountId")
  private Integer accountId;
  @Enumerated(EnumType.STRING)
  @JsonProperty("status")
  private StatusEnum status;
  @JsonProperty("ticker")
  private String ticker;
  @JsonProperty("size")
  private Integer size;
  @JsonProperty("price")
  private Double price;
  @JsonProperty("notes")
  private String notes;

  @JsonProperty("accountId")
  public Integer getAccountId() {
    return accountId;
  }

  @JsonProperty("accountId")
  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public SecurityOrder withAccountId(Integer accountId) {
    this.accountId = accountId;
    return this;
  }

  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(Integer id) {
    this.id = id;
  }

  public SecurityOrder withId(Integer id) {
    this.id = id;
    return this;
  }

  @JsonProperty("notes")
  public String getNotes() {
    return notes;
  }

  @JsonProperty("notes")
  public void setNotes(String notes) {
    this.notes = notes;
  }

  public SecurityOrder withNotes(String notes) {
    this.notes = notes;
    return this;
  }

  @JsonProperty("price")
  public Double getPrice() {
    return price;
  }

  @JsonProperty("price")
  public void setPrice(Double price) {
    this.price = price;
  }

  public SecurityOrder withPrice(Double price) {
    this.price = price;
    return this;
  }

  @JsonProperty("size")
  public Integer getSize() {
    return size;
  }

  @JsonProperty("size")
  public void setSize(Integer size) {
    this.size = size;
  }

  public SecurityOrder withSize(Integer size) {
    this.size = size;
    return this;
  }

  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  @JsonProperty("status")
  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public SecurityOrder withStatus(StatusEnum status) {
    this.status = status;
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

  public SecurityOrder withTicker(String ticker) {
    this.ticker = ticker;
    return this;
  }
  public enum StatusEnum {
    FILLED, CANCELED, PENDING
  }
}