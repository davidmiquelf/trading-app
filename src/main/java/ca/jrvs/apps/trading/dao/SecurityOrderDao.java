package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import java.sql.Types;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder, Long> {

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

}
