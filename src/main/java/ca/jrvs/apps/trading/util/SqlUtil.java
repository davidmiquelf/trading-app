package ca.jrvs.apps.trading.util;

import ca.jrvs.apps.trading.model.domain.Entity;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import java.lang.reflect.Field;

public class SqlUtil {

  /**
   * Builds a named-parameter-sql-update string for use in JdbcCrudDao.
   *
   * @param pojo Object being 'copied' to the database.
   * @param table Table being updated.
   * @param idName the name of the id field (which will correspond to the primary key.
   * @return An sql string.
   */
  public static String sqlUpdateString(Entity pojo, String table, String idName) {
    StringBuilder sqlBuilder = new StringBuilder("UPDATE ");
    sqlBuilder.append(table);
    sqlBuilder.append(" SET ");
    boolean first = true;
    for (Field field : pojo.getClass().getDeclaredFields()) {
      String fieldName = field.getName();
      if (fieldName == idName) {
        continue;
      }
      if (!first) {
        sqlBuilder.append(", ");
      }
      first = false;
      sqlBuilder.append(toSnakeCaseString(fieldName));
      sqlBuilder.append(" = :");
      sqlBuilder.append(fieldName);
    }
    sqlBuilder.append(" WHERE ");
    sqlBuilder.append(toSnakeCaseString(idName));
    sqlBuilder.append(" = :");
    sqlBuilder.append(idName);

    return sqlBuilder.toString();
  }

  /**
   * Converts a field name from javaStyle to database_style.
   *
   * @param name inJavaStyle
   * @return name in_database_style.
   */
  public static String toSnakeCaseString(String name) {
    PropertyNamingStrategy.SnakeCaseStrategy translator = new SnakeCaseStrategy();
    return translator.translate(name);
  }
}
