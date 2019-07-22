package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao extends JdbcCrudDao<Quote, String> {

  @Autowired
  public QuoteDao(DataSource dataSource) {
    super(dataSource, Quote.class, "quote", "ticker");

  }
}
