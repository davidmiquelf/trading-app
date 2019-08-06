package ca.jrvs.apps.trading.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.SecurityOrder.StatusEnum;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceUnitTest {

  //capture parameter when calling securityOrderDao.save
  @Captor
  ArgumentCaptor<SecurityOrder> captorSecurityOrder;

  //mock all dependencies
  @Mock
  private AccountDao accountDao;
  @Mock
  private SecurityOrderDao securityOrderDao;
  @Mock
  private QuoteDao quoteDao;
  @Mock
  private PositionDao positionDao;

  //injecting mocked dependencies to the testing class via constructor
  @InjectMocks
  private OrderService orderService;

  //setup test data
  private MarketOrderDto orderDto;
  private Quote quote;
  private Account account;
  private Position position;

  @Before
  public void setup() {
    orderDto = new MarketOrderDto().withAccountId(1).withSize(1L).withTicker("AAPL");
    quote = new Quote()
        .withAskSize(10L).withAskPrice(100.00)
        .withBidPrice(100.00).withBidSize(10L);
    account = new Account().withAmount(100.00).withId(orderDto.getAccountId());
    position = new Position().withPosition(11L).withTicker("AAPL").withAccountId(1);

    when(quoteDao.findById(orderDto.getTicker())).thenReturn(quote);
    when(accountDao.findById(orderDto.getAccountId())).thenReturn(account);
    when(positionDao.getByAccountIdAndTicker(orderDto.getAccountId(), orderDto.getTicker()))
        .thenReturn(position);

  }

  @Test
  public void executeMarketOrderHappyPath() {
    orderService.executeMarketOrder(orderDto);
    orderDto.setSize(Long.valueOf(-10));
    orderService.executeMarketOrder(orderDto);

    verify(securityOrderDao, times(2))
        .save(captorSecurityOrder.capture());
    List<SecurityOrder> captorOrders = captorSecurityOrder.getAllValues();
    assertEquals(StatusEnum.FILLED, captorOrders.get(0).getStatus());
    assertEquals(StatusEnum.FILLED, captorOrders.get(1).getStatus());
  }

  @Test
  public void executeMarketOrderSadPath() {
    orderDto.setSize(2L); //insufficient funds.
    orderService.executeMarketOrder(orderDto);
    account.setAmount(10000.00);
    orderDto.setSize(11L); //buy size larger than ask size.
    orderService.executeMarketOrder(orderDto);
    orderDto.setSize(11L); //sell size larger than bid size.
    orderService.executeMarketOrder(orderDto);
    orderDto.setSize(-12L); // not enough position.
    orderService.executeMarketOrder(orderDto);

    verify(securityOrderDao, times(4))
        .save(captorSecurityOrder.capture());
    List<SecurityOrder> captorOrders = captorSecurityOrder.getAllValues();
    for (SecurityOrder securityOrder : captorOrders) {
      assertEquals(StatusEnum.CANCELED, securityOrder.getStatus());
    }
  }

}