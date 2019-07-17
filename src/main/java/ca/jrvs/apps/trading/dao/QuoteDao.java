package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
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
  private DataSource dataSource;

  @Autowired
  public QuoteDao(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
    simpleJdbcInsert =
        new SimpleJdbcInsert(dataSource)
            .withTableName(TABLE_NAME)
            .usingGeneratedKeyColumns(ID_NAME);
  }

  @Override
  public Quote save(Quote quote) {
    SqlParameterSource params = new BeanPropertySqlParameterSource(quote);
    this.simpleJdbcInsert.execute(params);
    return quote;
  }

  @Override
  public Quote findById(String id) {
    String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
    return this.jdbcTemplate.queryForObject(sql, Quote.class, id);
  }

  @Override
  public boolean existsById(String id) {
    return findById(id) != null;
  }

  @Override
  public void deleteById(String id) {
    String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
    this.jdbcTemplate.update(sql);
  }
}
