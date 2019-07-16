package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonUtil;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.dao.DataRetrievalFailureException;

public class MarketDataDao {

  private HttpClientConnectionManager hccm;
  private final String BATCH_QUOTE_URL = "https://cloud.iexapis.com/stable/stock/market/batch"
      + "?symbols=%s&types=quote"
      + "&token=pk_f0966987a4e34207821ed24dbfdf9bb2";
  private final String SINGLE_QUOTE_URL = "https://cloud.iexapis.com/stable/stock/%s/quote"
      + "?token=pk_f0966987a4e34207821ed24dbfdf9bb2";


  public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager) {
    this.hccm = httpClientConnectionManager;
  }

  public List<IexQuote> findIexQuoteByTickers(List<String> tickers) {
    String tickersString = tickers.stream().collect(Collectors.joining(","));
    String url = String.format(BATCH_QUOTE_URL, tickersString);
    String json = executeHttpGet(url);
    List<IexQuote> quotes = null;
    try {
      quotes = JsonUtil.toObjectsFromJsonByField(json, "quote", IexQuote.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return quotes;
  }

  public IexQuote findIexQuoteByTicker(String ticker) {
    String url = String.format(SINGLE_QUOTE_URL, ticker);
    String json = executeHttpGet(url);
    IexQuote quote;
    try {
      quote = JsonUtil.toObjectFromJson(json, IexQuote.class);
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
