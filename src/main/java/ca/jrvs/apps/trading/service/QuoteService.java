package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DuplicateKeyException;

public class QuoteService {

  private QuoteDao quoteDao;
  private MarketDataDao marketDataDao;

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
      Quote quote = new Quote();
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
    Quote quote = new Quote();
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
