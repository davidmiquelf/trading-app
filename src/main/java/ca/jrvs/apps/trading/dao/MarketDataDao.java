package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;

@Repository
public class MarketDataDao {

  static final Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
  private HttpClientConnectionManager hccm;
  private final String HOST;
  private final String BATCH_QUOTE_URL = "stable/stock/market/batch"
      + "?symbols=%1$s&types=quote"
      + "&token=%2$s";
  private final String SINGLE_QUOTE_URL = "stable/stock/%1$s/quote"
      + "?token=%2$s";
  private final String TOKEN;


  @Autowired
  public MarketDataDao(
      HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig) {
    this.hccm = httpClientConnectionManager;
    this.HOST = marketDataConfig.getHost();
    this.TOKEN = marketDataConfig.getToken();
  }

  public List<IexQuote> findIexQuoteByTickers(List<String> tickers) {
    if (tickers == null || tickers.isEmpty()) {
      return new ArrayList<>();
    }
    String tickersString = String.join(",", tickers);
    String url = String.format(HOST + BATCH_QUOTE_URL, tickersString, TOKEN);
    String json = executeHttpGet(url);
    List<IexQuote> quotes = null;
    try {
      quotes = JsonUtil.toObjectsFromJsonByField(json, "quote", IexQuote.class);
    } catch (DataRetrievalFailureException e) {
      logger.debug("No data found.");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return quotes;
  }

  public IexQuote findIexQuoteByTicker(String ticker) {
    if (ticker == null) {
      return new IexQuote();
    }
    String url = String.format(HOST + SINGLE_QUOTE_URL, ticker, TOKEN);
    String json = executeHttpGet(url);
    IexQuote quote;
    try {
      quote = JsonUtil.toObjectFromJson(json, IexQuote.class);
    } catch (DataRetrievalFailureException e) {
      quote = new IexQuote();
      logger.debug("No data found.");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return quote;
  }

  protected String executeHttpGet(String url) {
    try (CloseableHttpClient httpClient = getHttpClient()) {
      HttpGet httpGet = new HttpGet(url);
      try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
        switch (response.getStatusLine().getStatusCode()) {
          case 200:
            //EntityUtils toString will also close inputStream in Entity
            String body = EntityUtils.toString(response.getEntity());
            return Optional.ofNullable(body).orElseThrow(
                () -> new IOException("Unexpected empty http response body"));
          case 404:
            throw new RuntimeException("Not Found");
          default:
            throw new DataRetrievalFailureException(
                "Unexpected status:" + response.getStatusLine().getStatusCode());
        }
      }
    } catch (IOException e) {
      throw new DataRetrievalFailureException("Unable Http execution error", e);
    }
  }

  private CloseableHttpClient getHttpClient() {
    return HttpClients.custom()
        .setConnectionManager(hccm)
        //prevent connectionManager shutdown when calling httpClient.close()
        .setConnectionManagerShared(true)
        .build();
  }

}
