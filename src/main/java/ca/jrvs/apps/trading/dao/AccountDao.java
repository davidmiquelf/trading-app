package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao extends JdbcCrudDao<Account, Integer> {

  @Autowired
  public AccountDao(DataSource dataSource) {
    super(dataSource, Account.class, "account", "id");
    this.simpleJdbcInsert.usingGeneratedKeyColumns("id");
  }

  public List<Account> findByTraderId(Integer traderId) {
    String sql = "SELECT * FROM account WHERE trader_id = ?";
    List<Account> accounts = null;
    try {
      accounts = this.jdbcTemplate
          .query(sql, BeanPropertyRowMapper.newInstance(Account.class), traderId);
    } catch (EmptyResultDataAccessException e) {
      logger.debug("Cannot find any accounts with trader_id = " + traderId, e);
    }
    if (accounts == null) {
      throw new ResourceNotFoundException("Resource not found.");
    }
    return accounts;
  }
}
