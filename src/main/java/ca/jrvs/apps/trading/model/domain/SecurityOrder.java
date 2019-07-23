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
public class SecurityOrder implements Entity<Long> {

  @JsonProperty("id")
  private Long id;
  @JsonProperty("accountId")
  private Long accountId;
  @Enumerated(EnumType.STRING)
  @JsonProperty("status")
  private StatusEnum status;
  @JsonProperty("ticker")
  private String ticker;
  @JsonProperty("size")
  private Long size;
  @JsonProperty("price")
  private Double price;
  @JsonProperty("notes")
  private Object notes;

  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(Long id) {
    this.id = id;
  }

  @JsonProperty("accountId")
  public Long getAccountId() {
    return accountId;
  }

  @JsonProperty("accountId")
  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  @JsonProperty("status")
  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  @JsonProperty("ticker")
  public String getTicker() {
    return ticker;
  }

  @JsonProperty("ticker")
  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  @JsonProperty("size")
  public Long getSize() {
    return size;
  }

  @JsonProperty("size")
  public void setSize(Long size) {
    this.size = size;
  }

  @JsonProperty("price")
  public Double getPrice() {
    return price;
  }

  @JsonProperty("price")
  public void setPrice(Double price) {
    this.price = price;
  }

  @JsonProperty("notes")
  public Object getNotes() {
    return notes;
  }

  @JsonProperty("notes")
  public void setNotes(Object notes) {
    this.notes = notes;
  }

  public enum StatusEnum {
    FILLED, CANCELED, PENDING
  }
}