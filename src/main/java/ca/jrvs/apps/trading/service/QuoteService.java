package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class QuoteService {

  private QuoteDao quoteDao;
  private MarketDataDao marketDataDao;

  @Autowired
  public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
    this.quoteDao = quoteDao;
    this.marketDataDao = marketDataDao;
  }

  /**
   * Helper method which map a IexQuote to a Quote entity. Note: `iexQuote.getLatestPrice() == null`
   * if the stock market is closed.
   */
  public static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
    Quote quote = new Quote();
    quote.setId(iexQuote.getSymbol());
    quote.setTicker(iexQuote.getSymbol());
    Double latestPrice = iexQuote.getLatestPrice();
    if (latestPrice != null) {
      quote.setLastPrice(latestPrice);
      quote.setBidPrice(iexQuote.getIexBidPrice());
      quote.setAskPrice(iexQuote.getIexAskPrice());
      quote.setBidSize(iexQuote.getIexBidSize());
      quote.setAskSize((iexQuote.getIexAskSize()));
    }
    return quote;
  }

  public void initQuotes(List<String> tickers) {
    for (String ticker : tickers) {
      Quote quote = new Quote()
          .withAskPrice(Double.valueOf(0))
          .withAskSize(Long.valueOf(0))
          .withBidPrice(Double.valueOf(0))
          .withBidSize(Long.valueOf(0))
          .withLastPrice(Double.valueOf(0));
      quote.setTicker(ticker);
      System.out.println("Trying to insert " + ticker + " to quotes.");
      try {
        quoteDao.save(quote);
      } catch (DuplicateKeyException e) {
        System.out.println(ticker + " already exists.");
      }

    }
  }

  public void initQuote(String ticker) {
    Quote quote = new Quote()
        .withAskPrice(Double.valueOf(0))
        .withAskSize(Long.valueOf(0))
        .withBidPrice(Double.valueOf(0))
        .withBidSize(Long.valueOf(0))
        .withLastPrice(Double.valueOf(0));
    quote.setTicker(ticker);
    System.out.println("Trying to insert " + ticker + " to quotes.");
    try {
      quoteDao.save(quote);
    } catch (DuplicateKeyException e) {
      System.out.println(ticker + " already exists.");
    }
  }

  public void updateMarketData() {
    List<Quote> quotes = quoteDao.getAll();
    List<String> tickers = quotes.stream()
        .map(Quote::getTicker)
        .collect(Collectors.toList());
    List<IexQuote> iexQuotes = marketDataDao.findIexQuoteByTickers(tickers);
    quotes = iexQuotes.stream()
        .map(QuoteService::buildQuoteFromIexQuote)
        .collect(Collectors.toList());
    quoteDao.updateAll(quotes);
  }
}
