package ca.jrvs.apps.trading.model.domain;

@SuppressWarnings("unused")
public class Quote implements Entity<String>, java.io.Serializable {

  private double askPrice;
  private int askSize;
  private double bidPrice;
  private int bidSize;
  private String id;
  private double lastPrice;
  private String ticker;

  public String getId() {
    return id;
  }

  public void setId(String s) {
    this.id = s;
  }

  public double getAskPrice() {
    return askPrice;
  }

  public void setAskPrice(double askPrice) {
    this.askPrice = askPrice;
  }

  public int getAskSize() {
    return askSize;
  }

  public void setAskSize(int askSize) {
    this.askSize = askSize;
  }

  public double getBidPrice() {
    return bidPrice;
  }

  public void setBidPrice(double bidPrice) {
    this.bidPrice = bidPrice;
  }

  public int getBidSize() {
    return bidSize;
  }

  public void setBidSize(int bidSize) {
    this.bidSize = bidSize;
  }

  public double getLastPrice() {
    return lastPrice;
  }

  public void setLastPrice(double lastPrice) {
    this.lastPrice = lastPrice;
  }

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }
}
