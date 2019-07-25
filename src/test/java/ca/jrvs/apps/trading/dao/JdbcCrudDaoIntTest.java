package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.SecurityOrder.StatusEnum;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.time.LocalDate;
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
public class JdbcCrudDaoIntTest {

  @Autowired
  private QuoteDao quoteDao;
  @Autowired
  private TraderDao traderDao;
  @Autowired
  private AccountDao accountDao;
  @Autowired
  private SecurityOrderDao securityOrderDao;

  @Test
  @Sql(scripts = "/test_setup.sql")
  public void saveAndExistsById() {
    Quote quote = new Quote();
    quote.setTicker("AACL");
    quote.setAskPrice(20.20);
    quote.setAskSize((long) 20);
    quote.setBidPrice(15.15);
    quote.setBidSize((long) 15);
    quote.setLastPrice(10.10);

    Trader trader = new Trader();
    trader.setFirstName("Fred");
    trader.setLastName("Weasley");
    trader.setDob("1990-10-10");
    trader.setCountry("England");
    trader.setEmail("fred.email@email.com");

    Account account = new Account();
    account.setAmount(100.0);

    SecurityOrder securityOrder = new SecurityOrder();
    securityOrder.setPrice(1.11);
    securityOrder.setSize(1);
    securityOrder.setStatus(StatusEnum.FILLED);
    securityOrder.setTicker("AACL");

    assertFalse(quoteDao.existsById("AACL"));
    assertEquals(quote, quoteDao.save(quote));

    trader = traderDao.save(trader);
    assertEquals(trader.getCountry(), "England");
    assertEquals(trader.getEmail(), "fred.email@email.com");

    account.setTraderId(trader.getId());
    account = accountDao.save(account);
    assertEquals(Double.valueOf(100.0), account.getAmount());

    securityOrder.setAccountId(account.getId());
    securityOrder = securityOrderDao.save(securityOrder);
    assertEquals(Integer.valueOf(1), securityOrder.getSize());

    assertTrue(quoteDao.existsById("AACL"));
  }

  @Test
  @Sql(scripts = "/test_setup.sql")
  public void findById() throws ResourceNotFoundException {
    Quote quote = quoteDao.findById("A");
    assertEquals((Double) 1.1, quote.getAskPrice());

    Trader trader = traderDao.findById(1);
    assertEquals("David", trader.getFirstName());

    Account account = accountDao.findById(1);
    assertEquals(account.getAmount(), Double.valueOf(100));

    SecurityOrder securityOrder = securityOrderDao.findById(1);
    assertEquals(StatusEnum.FILLED, securityOrder.getStatus());

  }

  @Test
  @Sql(scripts = "/test_setup.sql")
  public void getAll() {
    List<Quote> quotes = quoteDao.getAll();
    assertEquals(3, quotes.size());
    assertEquals("A", quotes.get(0).getTicker());

    List<Trader> traders = traderDao.getAll();
    assertEquals(2, traders.size());
    assertEquals("David", traders.get(0).getFirstName());

    List<Account> accounts = accountDao.getAll();
    assertEquals(2, accounts.size());
    assertEquals(Double.valueOf(100), accounts.get(0).getAmount());

    List<SecurityOrder> securityOrders = securityOrderDao.getAll();
    assertEquals(2, securityOrders.size());
    assertEquals(StatusEnum.FILLED, securityOrders.get(0).getStatus());
  }

  @Test
  public void update() {
    Trader trader = traderDao.findById(1);
    trader.setFirstName("Fred");
    trader.setDob("1990-10-10");
    trader.setEmail("fred.email@email.com");

    traderDao.update(trader);
    trader = traderDao.findById(1);

    assertEquals("Fred", trader.getFirstName());
    assertEquals("fred.email@email.com", trader.getEmail());
    assertEquals(LocalDate.parse("1990-10-10"), trader.getDob());

    Account account = accountDao.findById(1);
    account.setAmount(10000.0);
    accountDao.update(account);
    account = accountDao.findById(1);

    assertEquals(Double.valueOf(10000.0), account.getAmount());

    SecurityOrder securityOrder = securityOrderDao.findById(1);
    securityOrder.setStatus(StatusEnum.CANCELED);
    securityOrderDao.update(securityOrder);
    securityOrder = securityOrderDao.findById(1);

    assertEquals(StatusEnum.CANCELED, securityOrder.getStatus());

  }

  @Test
  @Sql(scripts = "/test_setup.sql")
  public void updateAll() {
    String[] tickers = {"A", "B", "C", "D"};
    List<Quote> quotes = new ArrayList<>();
    for (String ticker : tickers) {
      Quote quote = new Quote()
          .withTicker(ticker)
          .withAskSize(Long.valueOf(123))
          .withBidSize(Long.valueOf(123))
          .withAskPrice(123.12)
          .withBidPrice(123.12)
          .withLastPrice(123.12);
      quotes.add(quote);
    }
    quoteDao.updateAll(quotes);
    assertEquals(Integer.valueOf(123), quoteDao.findById("A").getAskSize());
    assertEquals(Integer.valueOf(123), quoteDao.findById("B").getAskSize());
    assertEquals(Integer.valueOf(123), quoteDao.findById("C").getAskSize());

    Integer[] ids = {1, 2};
    List<SecurityOrder> securityOrders = new ArrayList<>();
    for (Integer id : ids) {
      SecurityOrder securityOrder = securityOrderDao.findById(id);
      securityOrder.setId(id);
      securityOrder.setStatus(StatusEnum.CANCELED);
      securityOrders.add(securityOrder);
    }

    securityOrderDao.updateAll(securityOrders);
    assertEquals(StatusEnum.CANCELED, securityOrderDao.findById(1).getStatus());
    assertEquals(StatusEnum.CANCELED, securityOrderDao.findById(2).getStatus());

  }

  @Test
  @Sql(scripts = "/test_setup.sql")
  public void deleteById() {
    quoteDao.deleteById("A");
    quoteDao.deleteById("B");
    assertFalse(quoteDao.existsById("A"));

  }

  @Test
  @Sql(scripts = "/test_setup.sql")
  public void findByMethods() {
    List<Account> accounts = accountDao.findByTraderId(1);
    assertEquals(Double.valueOf(100.0), accounts.get(0).getAmount());

    List<SecurityOrder> orders = securityOrderDao.getAllByAccountId(1);
    assertEquals(StatusEnum.FILLED, orders.get(0).getStatus());
  }
}