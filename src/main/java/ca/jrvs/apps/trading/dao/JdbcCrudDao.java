package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcCrudDao<E extends Entity> implements CrudRepository<E, String> {

  protected final String TABLE_NAME;
  protected final String ID_NAME;
  protected JdbcTemplate jdbcTemplate;
  protected SimpleJdbcInsert simpleJdbcInsert;
  protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private Class<E> eClass;

  public JdbcCrudDao(DataSource dataSource, Class<E> clazz, String tableName, String idName) {
    this.eClass = clazz;
    this.TABLE_NAME = tableName;
    this.ID_NAME = idName;
    jdbcTemplate = new JdbcTemplate(dataSource);
    namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    simpleJdbcInsert =
        new SimpleJdbcInsert(dataSource)
            .withTableName(TABLE_NAME);

  }

  @Override
  public E save(E entity) {
    SqlParameterSource params = new BeanPropertySqlParameterSource(entity);
    try {
      this.simpleJdbcInsert.execute(params);
    } catch (DuplicateKeyException e) {
      System.out.println(entity.getId() + " is already in " + TABLE_NAME);
    }
    return entity;
  }

  @Override
  public E findById(String id) {
    String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_NAME + " = ?";
    E entity = this.jdbcTemplate.queryForObject(
        sql, BeanPropertyRowMapper.newInstance(this.eClass), id);
    return entity;
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

  public List<E> getAll() {
    List<E> entities = this.jdbcTemplate.query(
        "select * from " + TABLE_NAME,
        BeanPropertyRowMapper.newInstance(this.eClass));
    return entities;
  }

  public void updateAll(List<E> entities, String sql) {
    SqlParameterSource[] params = entities.stream()
        .map(BeanPropertySqlParameterSource::new)
        .toArray(SqlParameterSource[]::new);

    this.namedParameterJdbcTemplate.batchUpdate(sql, params);

  }

}
