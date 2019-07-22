package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Trader;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TraderDao extends JdbcCrudDao<Trader, Long> {

  @Autowired
  public TraderDao(DataSource dataSource) {
    super(dataSource, Trader.class, "trader", "id");
  }

  public void updateAll(List<Trader> quotes) {
    String sql =
        "Update trader Set "
            + "first_name = :firstName, "
            + "last_name = :lastName, "
            + "dob = :dob, "
            + "country = :country, "
            + "email = :email, "
            + "WHERE id = :id";
    super.updateAll(quotes, sql);
  }
}
