package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao extends JdbcCrudDao<Quote> {

  @Autowired
  public QuoteDao(DataSource dataSource) {
    super(dataSource, Quote.class, "quote", "ticker");

  }

  public void update(List<Quote> quotes) {
    String sql =
        "Update quote Set "
            + "last_price = :lastPrice, "
            + "bid_price = :bidPrice, "
            + "bid_size = :bidSize, "
            + "ask_price = :askPrice, "
            + "ask_size = :askSize "
            + "WHERE ticker = :ticker";
    super.updateAll(quotes, sql);
  }
}
