package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.trading.model.domain.Position;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ca.jrvs.apps.trading.TestConfig.class,
    loader = AnnotationConfigContextLoader.class)
public class PositionDaoIntTest {

  @Autowired
  private PositionDao positionDao;

  @Test
  @Sql(scripts = "/test_setup.sql")
  public void getAll() {
    List<Position> positions = positionDao.getAll();
    assertEquals(positions.size(), 2);
    assertEquals(Integer.valueOf(1), positions.get(0).getAccountId());

    positions = positionDao.getAllByTicker("C");
    assertEquals(1, positions.size());
    assertEquals(Integer.valueOf(1), positions.get(0).getAccountId());

    positions = positionDao.getAllByAccountId(1);
    assertEquals(1, positions.size());
    assertEquals("C", positions.get(0).getTicker());
  }

  @Test
  @Sql(scripts = "/test_setup.sql")
  public void getByTickerAndAccountId() {
    Position position = positionDao.getByAccountIdAndTicker(1, "C");
    assertEquals(Long.valueOf(1), position.getPosition());
  }
}