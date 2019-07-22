package ca.jrvs.apps.trading;

import javax.sql.DataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class AppConfig {


  @Value("${iex.host}")
  private String iex_host;

  /*
      @Bean
    public MarketDataConfig marketDataConfig() {
    }
  */
    @Bean
    public DataSource dataSource() {
      DataSource dataSource = new SimpleDriverDataSource();
      return dataSource;
    }

  //http://bit.ly/2tWTmzQ connectionPool
  @Bean
  public HttpClientConnectionManager httpClientConnectionManager() {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(50);
    cm.setDefaultMaxPerRoute(50);
    return cm;
  }
}
