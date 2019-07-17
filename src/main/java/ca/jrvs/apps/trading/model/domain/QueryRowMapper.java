package ca.jrvs.apps.trading.model.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class QueryRowMapper implements RowMapper {

  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    Quote quote = new Quote();
    quote.setId(rs.getString("ticker"));
    quote.setBidSize(rs.getLong("bid_size"));
    quote.setAskPrice(rs.getDouble("ask_price"));
    quote.setBidPrice(rs.getDouble("bid_price"));
    quote.setAskSize(rs.getLong("ask_size"));
    quote.setTicker(rs.getString("ticker"));
    quote.setLastPrice(rs.getDouble("last_price"));
    return quote;
  }

}