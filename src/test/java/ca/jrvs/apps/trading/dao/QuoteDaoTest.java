package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

import ca.jrvs.apps.trading.model.domain.Quote;
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
  @Sql(
      statements = "TRUNCATE quote CASCADE",
      executionPhase = AFTER_TEST_METHOD
  )
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
  public void deleteById() {
  }
}