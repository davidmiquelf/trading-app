package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Trader;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TraderDao extends JdbcCrudDao<Trader, Long> {

  @Autowired
  public TraderDao(DataSource dataSource) {
    super(dataSource, Trader.class, "trader", "id");
  }

}
