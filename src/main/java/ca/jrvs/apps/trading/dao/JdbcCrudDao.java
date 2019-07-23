package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import ca.jrvs.apps.trading.util.SqlUtil;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public abstract class JdbcCrudDao<E extends Entity, ID> implements CrudRepository<E, ID> {

  private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);
  private Class<E> eClass;

  private final String TABLE_NAME;
  private final String ID_NAME;
  boolean hasGeneratedKey = true;
  SimpleJdbcInsert simpleJdbcInsert;
  private JdbcTemplate jdbcTemplate;
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  JdbcCrudDao(DataSource dataSource, Class<E> clazz, String tableName, String idName) {
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
  @SuppressWarnings("unchecked")
  public E save(E entity) {
    SqlParameterSource params = getParameterSource(entity);
    try {
      if (hasGeneratedKey) {
        Number id = this.simpleJdbcInsert.executeAndReturnKey(params);
        entity.setId(id.longValue());
      } else {
        this.simpleJdbcInsert.execute(params);
      }
    } catch (DuplicateKeyException e) {
      logger.debug(entity.getId() + " is already in " + TABLE_NAME);
    } catch (InvalidDataAccessApiUsageException e) {
      logger.debug(TABLE_NAME + " has no generated keys.");
    }
    return entity;
  }

  @Override
  public E findById(ID id) {
    String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_NAME + " = ?";
    logger.debug(sql);
    E entity = null;
    try {
      entity = this.jdbcTemplate.queryForObject(
          sql, BeanPropertyRowMapper.newInstance(this.eClass), id);
    } catch (EmptyResultDataAccessException e) {
      logger.debug("Cannot find entity in with " + ID_NAME + " = " + id, e);
    }
    if (entity == null) {
      throw new ResourceNotFoundException("Resource not found.");
    }

    return entity;
  }

  @Override
  public boolean existsById(ID id) {
    String sql = "SELECT count(*) FROM " + TABLE_NAME + " WHERE " + ID_NAME + " = ?";
    logger.debug(sql);
    Integer count = this.jdbcTemplate.queryForObject(sql, Integer.class, id);
    return count != null && count > 0;
  }

  @Override
  public void update(E entity) {
    String sql = SqlUtil.sqlUpdateString(entity, this.TABLE_NAME, this.ID_NAME);
    logger.debug(sql);
    SqlParameterSource params = getParameterSource(entity);
    this.namedParameterJdbcTemplate.update(sql, params);
  }

  @Override
  public void deleteById(ID id) {
    String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_NAME + " = ?";
    logger.debug(sql);
    try {
      this.jdbcTemplate.update(sql, id);
    } catch (DataIntegrityViolationException e) {
      logger.debug("Cannot delete: This row is referenced by other tables.");
    }

  }

  public List<E> getAll() {
    String sql = "select * from " + TABLE_NAME;
    logger.debug(sql);
    return this.jdbcTemplate.query(sql,
        BeanPropertyRowMapper.newInstance(this.eClass));
  }

  /**
   * For each entity, update the corresponding entry in the database. Uses reflection to determine
   * the field names. See JsonUtil.
   *
   * @param entities contain new values for database entries.
   */
  public void updateAll(List<E> entities) {
    if (entities.isEmpty()) {
      logger.debug("No entities given.");
      return;
    }
    E entity = entities.get(0);
    String sql = SqlUtil.sqlUpdateString(entity, this.TABLE_NAME, this.ID_NAME);
    logger.debug(sql);
    SqlParameterSource[] params = entities.stream()
        .map(this::getParameterSource)
        .toArray(SqlParameterSource[]::new);

    this.namedParameterJdbcTemplate.batchUpdate(sql, params);

  }

  /**
   * Helper function. A DAO can override this to register specific parameters.
   */
  protected BeanPropertySqlParameterSource getParameterSource(E entity) {
    return new BeanPropertySqlParameterSource(entity);
  }

}
