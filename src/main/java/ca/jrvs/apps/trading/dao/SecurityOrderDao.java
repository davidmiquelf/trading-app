package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;

public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder, Long> {

  @Autowired
  public SecurityOrderDao(DataSource dataSource) {
    super(dataSource, SecurityOrder.class, "security_order", "id");
  }
}
