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

  public List<Position> getAllByAccountId(Integer accountId) {
    logger.debug("getAllByAccount: accountId= " + accountId.toString());
    String sql = "SELECT * FROM position WHERE account_id = ?";
    List<Position> positions = null;
    try {
      positions = this.jdbcTemplate
          .query(sql, BeanPropertyRowMapper.newInstance(Position.class), accountId);
    } catch (EmptyResultDataAccessException e) {
      logger.debug("Cannot find any position with accountId = " + accountId);
    }
    if (positions == null) {
      throw new ResourceNotFoundException("Resource not found.");
    }
    return positions;
  }

  public List<Position> getAllByTicker(String ticker) {
    logger.debug("getAllByTicker: accountId= " + ticker);
    String sql = "SELECT * FROM position WHERE ticker = ?";
    List<Position> positions = null;
    try {
      positions = this.jdbcTemplate
          .query(sql, BeanPropertyRowMapper.newInstance(Position.class), ticker);
    } catch (EmptyResultDataAccessException e) {
      logger.debug("Cannot find any position with ticker = " + ticker);
    }
    if (positions == null) {
      throw new ResourceNotFoundException("Resource not found.");
    }
    return positions;
  }

  public Position getByAccountIdAndTicker(Integer accountId, String ticker) {
    logger.debug("accountId= " + accountId.toString() + ", ticker = " + ticker);
    String sql = "SELECT * FROM position WHERE account_id = ? AND ticker = ?";
    Position position = null;
    try {
      position = this.jdbcTemplate.queryForObject(
          sql, BeanPropertyRowMapper.newInstance(Position.class), accountId, ticker);
    } catch (EmptyResultDataAccessException e) {
      logger.debug("Can't find position with accountId = " + accountId + " and ticker = " + ticker);
    }
    if (position == null) {
      throw new ResourceNotFoundException("Resource not found.");
    }

    return position;
  }
}
