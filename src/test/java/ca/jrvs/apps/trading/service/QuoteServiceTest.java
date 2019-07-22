package ca.jrvs.apps.trading.service;

import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ca.jrvs.apps.trading.TestConfig.class,
    loader = AnnotationConfigContextLoader.class)
public class QuoteServiceTest {

  @Autowired
  private QuoteDao quoteDao;
  @Autowired
  private MarketDataDao marketDataDao;

  @Test
  @Sql(statements = "Truncate quote cascade")
  public void integrationTest() {
    QuoteService service = new QuoteService(quoteDao, marketDataDao);
    String[] tickers = {"FB", "AAPL", "NFLX", "ATVI"};
    List<String> tickerList = Arrays.asList(tickers);

    service.initQuotes(tickerList);
    assertTrue(quoteDao.existsById("FB"));
    assertTrue(quoteDao.existsById("AAPL"));
    assertTrue(quoteDao.existsById("NFLX"));
    assertTrue(quoteDao.existsById("ATVI"));

    service.updateMarketData();
    if (marketDataDao.findIexQuoteByTicker("FB").getLatestPrice() != null) {
      assertTrue(quoteDao.findById("FB").getLastPrice() > 0);
    }
  }
}