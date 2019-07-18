package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
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
  public void integrationTest() {
    QuoteService service = new QuoteService(quoteDao, marketDataDao);
    String[] tickers = {"FB", "AAPL", "NFLX", "ATVI"};
    List<String> tickerList = Arrays.asList(tickers);
    service.initQuotes(tickerList);
    service.updateMarketData();
  }
}