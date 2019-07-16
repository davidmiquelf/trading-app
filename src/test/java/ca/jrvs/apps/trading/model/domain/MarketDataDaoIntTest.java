package ca.jrvs.apps.trading.model.domain;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.junit.BeforeClass;
import org.junit.Test;

public class MarketDataDaoIntTest {
  private static MarketDataDao dao;

  @BeforeClass
  public static void setup(){
    HttpClientConnectionManager hccm = new BasicHttpClientConnectionManager();
    dao = new MarketDataDao(hccm);
  }
  @Test
  public void findIexQuoteByTickers() {
    List<String> tickers = new ArrayList<String>();
    tickers.add("aapl");
    tickers.add("fb");
    List<IexQuote> quotes = dao.findIexQuoteByTickers(tickers);
    assertEquals("AAPL", quotes.get(0).getSymbol());
  }

  @Test
  public void findIexQuoteByTicker() {
    String ticker = "aapl";
    IexQuote quote = dao.findIexQuoteByTicker(ticker);
    assertEquals("AAPL", quote.getSymbol());
  }
}