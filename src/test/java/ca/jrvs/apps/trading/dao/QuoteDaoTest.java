package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
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
public class QuoteDaoTest {

  @Autowired
  private QuoteDao quoteDao;

  @Test
  public void saveAndExistsById() {
    Quote quote = new Quote();
    quote.setTicker("AACL");
    quote.setAskPrice(20.20);
    quote.setAskSize((long) 20);
    quote.setBidPrice(15.15);
    quote.setBidSize((long) 15);
    quote.setLastPrice(10.10);

    assertFalse(quoteDao.existsById("AACL"));

    assertEquals(quote, quoteDao.save(quote));

    assertTrue(quoteDao.existsById("AACL"));
  }

  @Test
  @Sql(scripts = "/quote.sql")
  public void findById() {
    Quote quote = quoteDao.findById("A");
    assertEquals((Double) 1.1, quote.getAskPrice());

  }

  @Test
  @Sql(scripts = "/quote.sql")
  public void getAll() {
    List<Quote> quotes = quoteDao.getAll();
    assertEquals(3, quotes.size());
  }

  @Test
  @Sql(scripts = "/quote.sql")
  public void updateAll() {
    String[] tickers = {"A", "B", "C", "D"};
    List<Quote> quotes = new ArrayList<>();
    for (String ticker : tickers) {
      Quote quote = new Quote();
      quote.setTicker(ticker);
      quote.setAskSize((long) 123);
      quotes.add(quote);
    }
    quoteDao.update(quotes);
    assertEquals((long) 123, (long) quoteDao.findById("A").getAskSize());
    assertEquals((long) 123, (long) quoteDao.findById("B").getAskSize());
    assertEquals((long) 123, (long) quoteDao.findById("C").getAskSize());

  }

  @Test
  @Sql(scripts = "/quote.sql")
  public void deleteById() {
    quoteDao.deleteById("A");
    quoteDao.deleteById("B");
    quoteDao.deleteById("C");
    assertFalse(quoteDao.existsById("A"));
    assertFalse(quoteDao.existsById("B"));
    assertFalse(quoteDao.existsById("C"));
  }


}