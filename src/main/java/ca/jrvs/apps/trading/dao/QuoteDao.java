package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

  private final static String TABLE_NAME = "quote";
  private final static String ID_NAME = "ticker";
  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public QuoteDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
    simpleJdbcInsert =
        new SimpleJdbcInsert(dataSource)
            .withTableName(TABLE_NAME);
  }

  @Override
  public Quote save(Quote quote) {
    SqlParameterSource params = new BeanPropertySqlParameterSource(quote);
    this.simpleJdbcInsert.execute(params);
    return quote;
  }

  @Override
  public Quote findById(String id) {
    String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_NAME + " = ?";
    Quote quote = this.jdbcTemplate.queryForObject(
        sql, BeanPropertyRowMapper.newInstance(Quote.class), id);
    return quote;
  }

  @Override
  public boolean existsById(String id) {
    String sql = "SELECT count(*) FROM " + TABLE_NAME + " WHERE " + ID_NAME + " = ?";
    boolean exists = false;
    Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class, id);
    exists = count != null && count > 0;
    return exists;
  }

  @Override
  public void deleteById(String id) {
    String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_NAME + " = ?";
    this.jdbcTemplate.update(sql, id);
  }

  public List<Quote> getAll() {
    List<Quote> quotes = this.jdbcTemplate.query(
        "select * from quote",
        BeanPropertyRowMapper.newInstance(Quote.class));
    return quotes;
  }
}
