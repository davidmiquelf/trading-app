package ca.jrvs.apps.trading.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountId",
        "size",
        "ticker"
})
public class MarketOrderDto {

    @JsonProperty("accountId")
    private Long accountId;
    @JsonProperty("size")
    private Long size;
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

    public MarketOrderDto withAccountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    @JsonProperty("size")
    public Long getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(Long size) {
        this.size = size;
    }

    public MarketOrderDto withSize(Long size) {
        this.size = size;
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

    public MarketOrderDto withTicker(String ticker) {
        this.ticker = ticker;
        return this;
    }

}
