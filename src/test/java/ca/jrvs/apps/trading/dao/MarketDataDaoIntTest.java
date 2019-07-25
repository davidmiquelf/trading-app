package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
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
    MarketDataConfig marketDataConfig = new MarketDataConfig();
    marketDataConfig.setHost("https://cloud.iexapis.com/");
    marketDataConfig.setToken("pk_f0966987a4e34207821ed24dbfdf9bb2");
    dao = new MarketDataDao(hccm, marketDataConfig);
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