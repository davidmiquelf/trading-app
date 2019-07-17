package ca.jrvs.apps.trading.dao;

public interface CrudRepository<E, ID> {

  /**
   * Create a given entity. Return saved entity.
   *
   * @param entity must not be {@literal null}
   * @return saved entity.
   * @throws IllegalArgumentException if entity is invalid.
   * @throws java.sql.SQLException if sql execution failed.
   */
  E save(E entity);

  /**
   * Retrieves an entity by its id
   *
   * @param id must not be {@literal null}
   * @return the entity with the gievn id, or null if not found.
   */
  E findById(ID id);

  /**
   * Returns whether an entity with the given id exists.
   *
   * @param id must not be {@literal null}
   * @return {@literal true} if exists, {@literal false} otherwise.
   */
  boolean existsById(ID id);

  /**
   * Delete the entity with the given id.
   *
   * @param id must not be {@literal null}
   */
  void deleteById(ID id);
}
