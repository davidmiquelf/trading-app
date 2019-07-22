package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountDao extends JdbcCrudDao<Account, Long> {

  @Autowired
  public AccountDao(DataSource dataSource) {
    super(dataSource, Account.class, "trader", "id");
  }


}
