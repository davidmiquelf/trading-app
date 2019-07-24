package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao {

  private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);
  private JdbcTemplate jdbcTemplate;

  @Autowired
  public PositionDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public List<Position> getAll() {
    String sql = "SELECT * FROM position";
    List<Position> positions = null;
    try {
      positions = this.jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Position.class));
    } catch (EmptyResultDataAccessException e) {
      logger.debug("There are no positions.");
    }
    if (positions == null) {
      throw new ResourceNotFoundException("Resource not found");
    }
    return positions;
  }

  public Position getByAccountIdAndTicker(Long accountId, String ticker) {
    logger.debug("accountId= " + accountId.toString() + ", ticker = " + ticker);
    String sql = "SELECT * FROM position WHERE account_id = ? AND ticker = ?";
    Position position = null;
    try {
      position = this.jdbcTemplate.queryForObject(
          sql, BeanPropertyRowMapper.newInstance(Position.class), accountId, ticker);
    } catch (EmptyResultDataAccessException e) {
      logger.debug("Cannot find position with accountId = " + accountId + " and ticker ");
    }
    if (position == null) {
      throw new ResourceNotFoundException("Resource not found.");
    }

    return position;
  }
}
