package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.model.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FundTransferService {

  private AccountDao accountDao;

  @Autowired
  public FundTransferService(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  /**
   * Deposit a fund to the account which is associated with the Id
   * - validate user input
   * - account = accountDao.findById
   * - accountDao.updateAmountById
   *
   * @param accountId account id
   * @param fund      found amount (can't be 0)
   * @return updated Account object
   * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
   * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
   * @throws IllegalArgumentException                           for invalid input
   */
  public Account deposit(Integer accountId, Double fund) {
    if (fund <= 0) {
      throw new IllegalArgumentException("Must deposit a strictly positive amount.");
    }
    if (!accountDao.existsById(accountId)) {
      throw new IllegalArgumentException("Invalid account id.");
    }
    Account account = accountDao.findById(accountId);
    account.setAmount(account.getAmount() + fund);
    accountDao.update(account);
    return account;
  }

  /**
   * Withdraw a fund from the account which is associated with the Id
   * <p>
   * - validate user input
   * - account = accountDao.findById
   * - accountDao.updateAmountById
   *
   * @param accountId account ID
   * @param fund      amount can't be 0
   * @return updated Account object
   * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
   * @throws org.springframework.dao.DataAccessException        if unable to retrieve data
   * @throws IllegalArgumentException                           for invalid input
   */
  public Account withdraw(Integer accountId, Double fund) {
    if (fund <= 0) {
      throw new IllegalArgumentException("Must withdraw a strictly positive amount.");
    }
    Account account = accountDao.findById(accountId);
    if (account.getAmount() < fund) {
      throw new IllegalArgumentException("Insufficient funds.");
    }
    account.setAmount(account.getAmount() - fund);
    accountDao.update(account);
    return account;
  }
}
