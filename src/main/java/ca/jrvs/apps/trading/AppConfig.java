package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.util.StringUtil;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:/application-dev.properties")
@EnableTransactionManagement
public class AppConfig {


  @Value("${iex.host}")
  private String iex_host;

  @Bean
  public MarketDataConfig marketDataConfig() {
    if (StringUtil.isEmpty(System.getenv("IEX_PUB_TOKEN")) || StringUtil.isEmpty(iex_host)) {
      throw new IllegalArgumentException("ENV:IEX_PUB_TOKEN or property:iex_host is not set");
    }
    MarketDataConfig marketDataConfig = new MarketDataConfig();
    marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));
    marketDataConfig.setHost(iex_host);
    return marketDataConfig;
  }

  @Bean
  public DataSource dataSource() {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setUrl("jdbc:postgresql://localhost:5432/jrvstrading");
    dataSource.setUsername("postgres");
    dataSource.setPassword("password");
    return dataSource;
  }

  @Bean
  public HttpClientConnectionManager httpClientConnectionManager() {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(50);
    cm.setDefaultMaxPerRoute(50);
    return cm;
  }
}
