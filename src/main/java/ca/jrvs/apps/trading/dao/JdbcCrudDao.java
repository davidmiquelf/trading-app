package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import ca.jrvs.apps.trading.util.SqlUtil;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbcCrudDao<E extends Entity, ID> implements CrudRepository<E, ID> {

  private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);
  private Class<E> eClass;

  protected final String TABLE_NAME;
  protected final String ID_NAME;
  protected JdbcTemplate jdbcTemplate;
  protected SimpleJdbcInsert simpleJdbcInsert;
  protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
      logger.debug(entity.getId() + " is already in " + TABLE_NAME);
    }
    return entity;
  }

  @Override
  public E findById(ID id) throws ResourceNotFoundException {
    String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_NAME + " = ?";
    logger.info(sql);
    E entity = null;
    try {
      entity = this.jdbcTemplate.queryForObject(
          sql, BeanPropertyRowMapper.newInstance(this.eClass), id);
    } catch (EmptyResultDataAccessException e) {
      logger.debug("Can't find trader id:" + id, e);
    }
    if (entity == null) {
      throw new ResourceNotFoundException("Resource not found.");
    }

    return entity;
  }

  @Override
  public boolean existsById(ID id) {
    String sql = "SELECT count(*) FROM " + TABLE_NAME + " WHERE " + ID_NAME + " = ?";
    logger.info(sql);
    boolean exists = false;
    Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class, id);
    exists = count != null && count > 0;
    return exists;
  }

  @Override
  public void deleteById(ID id) {
    String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_NAME + " = ?";
    logger.info(sql);
    this.jdbcTemplate.update(sql, id);
  }

  public List<E> getAll() {
    String sql = "select * from " + TABLE_NAME;
    logger.info(sql);
    List<E> entities = this.jdbcTemplate.query(sql,
        BeanPropertyRowMapper.newInstance(this.eClass));
    return entities;
  }

  public void updateAll(List<E> entities) {
    if (entities.isEmpty()) {
      return;
    }
    E entity = entities.get(0);
    String sql = SqlUtil.sqlUpdateString(entity, this.TABLE_NAME, this.ID_NAME);
    logger.info(sql);
    SqlParameterSource[] params = entities.stream()
        .map(BeanPropertySqlParameterSource::new)
        .toArray(SqlParameterSource[]::new);

    this.namedParameterJdbcTemplate.batchUpdate(sql, params);

  }

}
