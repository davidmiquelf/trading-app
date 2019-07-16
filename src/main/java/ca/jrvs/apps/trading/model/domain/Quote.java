package ca.jrvs.apps.trading.model.domain;

public class Quote implements Entity<String>{
  public double askPrice;
  public int askSize;
  public double bidPrice;
  public int bidSize;
  public String id;
  public double lastPrice;
  public String ticker;

  public String getId() {
    return id;
  }

  public void setId(String s) {
    this.id = s;
  }
}
