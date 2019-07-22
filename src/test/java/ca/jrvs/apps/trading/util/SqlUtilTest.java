package ca.jrvs.apps.trading.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.Test;

public class SqlUtilTest {

  @Test
  public void sqlUpdateString() {
    Quote quote = new Quote();
    quote.setTicker("A");
    quote.setAskSize((long) 1);
    quote.setAskPrice(1.1);
    quote.setBidPrice(1.1);
    quote.setBidSize((long) 2);
    String sql_actual = SqlUtil.sqlUpdateString(quote, "quote", "ticker");
    assertTrue(sql_actual.matches("UPDATE quote SET .*"));
    assertTrue(sql_actual.matches(".*ticker = :ticker.*"));
    System.out.println(sql_actual);
    String sql =
        "UPDATE quote SET "
            + "last_price = :lastPrice, "
            + "bid_price = :bidPrice, "
            + "bid_size = :bidSize, "
            + "ask_price = :askPrice, "
            + "ask_size = :askSize "
            + "WHERE ticker = :ticker";
    assertEquals(sql, sql_actual);
  }
}