package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import java.sql.Types;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder, Integer> {

  private static final Logger logger = LoggerFactory.getLogger(SecurityOrder.class);

  @Autowired
  public SecurityOrderDao(DataSource dataSource) {
    super(dataSource, SecurityOrder.class, "security_order", "id");
    this.simpleJdbcInsert.usingGeneratedKeyColumns("id");
  }

  @Override
  protected BeanPropertySqlParameterSource getParameterSource(SecurityOrder securityOrder) {
    BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(securityOrder);
    params.registerSqlType("status", Types.VARCHAR);
    return params;
  }

  public List<SecurityOrder> getAllByAccountId(Integer accountId) {
    String sql = "SELECT * FROM security_order WHERE account_id = ?";
    List<SecurityOrder> orders = null;
    try {
      orders = this.jdbcTemplate
          .query(sql, BeanPropertyRowMapper.newInstance(SecurityOrder.class), accountId);
    } catch (EmptyResultDataAccessException e) {
      logger.debug("Cannot find any securityOrder with accountId = " + accountId);
    }
    if (orders == null) {
      throw new ResourceNotFoundException("Resource not found.");
    }
    return orders;
  }

}
