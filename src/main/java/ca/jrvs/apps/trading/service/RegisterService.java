package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.util.JsonUtil;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

  private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

  private TraderDao traderDao;
  private AccountDao accountDao;
  private PositionDao positionDao;
  private SecurityOrderDao securityOrderDao;

  @Autowired
  public RegisterService(TraderDao traderDao, AccountDao accountDao,
      PositionDao positionDao, SecurityOrderDao securityOrderDao) {
    this.traderDao = traderDao;
    this.accountDao = accountDao;
    this.positionDao = positionDao;
    this.securityOrderDao = securityOrderDao;
  }

  /**
   * Create a new trader and initialize a new account with 0 amount.
   * - validate user input (allfields must be non empty)
   * - create a trader
   * - create an account
   * - create, setup, and return a new traderAccountView
   *
   * @param trader trader info
   * @return traderAccountView
   * @throws org.springframework.dao.DataAccessException if unable to retrieve data
   * @throws IllegalArgumentException                    for invalid input
   */
  public TraderAccountView createTraderAndAccount(Trader trader) {
    try {
      JsonUtil.validateEntity(Trader.class, trader, "id");
    } catch (IllegalAccessException | NullPointerException e) {
      logger.error(e.getMessage());
    }
    trader = traderDao.save(trader);
    logger.debug("trader id: " + trader.getId());

    Account account = new Account()
        .withTraderId(trader.getId())
        .withAmount(Double.valueOf(0));
    account = accountDao.save(account);
    logger.debug("account id: " + account.getId());

    TraderAccountView view = new TraderAccountView()
        .withTrader(trader)
        .withAccount(account);
    return view;
  }

  /**
   * A trader can be deleted iff no open position and no cash balance.
   * - validate traderID
   * - get trader account by traderId and check account balance
   * - get positions by accountId and check positions
   * - delete all securityOrders, account, trader (in this order)
   *
   * @param traderId
   * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
   * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
   * @throws IllegalArgumentException                           for invalid input
   */
  public void deleteTraderById(Integer traderId) {
    //This checks if the id exists.
    Trader trader = traderDao.findById(traderId);
    List<Account> accounts = accountDao.findByTraderId(traderId);

    for (Account account : accounts) {
      if (account.getAmount() != 0) {
        throw new IllegalArgumentException("Can't delete trader due to non-zero account amount");
      }
      List<Position> positions = positionDao.getAllByAccountId(account.getId());
      for (Position position : positions) {
        if (position.getPosition() != 0) {
          throw new IllegalArgumentException("Can't delete trader due to non-closed position");
        }
      }
    }
    //For each account, delete associated security orders. then delete the accounts.
    accounts.stream()
        .peek(account -> securityOrderDao.getAllByAccountId(account.getId())
            .stream()
            .map(SecurityOrder::getId)
            .forEach(securityOrderDao::deleteById))
        .map(Account::getId)
        .forEach(accountDao::deleteById);

    traderDao.deleteById(traderId);
  }

  public List<TraderAccountView> viewAccounts(Integer traderId) {
    List<Account> accounts = accountDao.findByTraderId(traderId);
    List<TraderAccountView> views = new ArrayList<>();
    Trader trader = traderDao.findById(traderId);
    for (Account account : accounts) {
      views.add(new TraderAccountView().withAccount(account).withTrader(trader));
    }
    return views;
  }

}