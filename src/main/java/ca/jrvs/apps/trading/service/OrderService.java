package ca.jrvs.apps.trading.service;

import static ca.jrvs.apps.trading.model.domain.SecurityOrder.StatusEnum;
import static java.lang.Math.abs;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.dto.MarketOrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private AccountDao accountDao;
    private SecurityOrderDao securityOrderDao;
    private QuoteDao quoteDao;
    private PositionDao positionDao;

    @Autowired
    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao,
                        QuoteDao quoteDao, PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }

    /**
     * Execute a market order
     * <p>
     * - validate the order (e.g. size, and ticker)
     * - Create a securityOrder (for security_order table)
     * - Handle buy or sell order
     * - buy order : check account balance
     * - sell order: check position for the ticker/symbol
     * - (please don't forget to update securityOrder.status)
     * - Save and return securityOrder
     * <p>
     * NOTE: you will need to some helper methods (protected or private)
     *
     * @param orderDto market order
     * @return SecurityOrder from security_order table
     * @throws org.springframework.dao.DataAccessException if unable to get data from DAO
     * @throws IllegalArgumentException                    for invalid input
     */
    public SecurityOrder executeMarketOrder(MarketOrderDto orderDto) {
        SecurityOrder securityOrder = new SecurityOrder()
                .withAccountId(orderDto.getAccountId())
                .withTicker(orderDto.getTicker())
                .withSize(orderDto.getSize());

        if (orderDto.getSize() > 0) {
            executeBuyOrder(securityOrder, orderDto);
        } else if (orderDto.getSize() < 0) {
            executeSellOrder(securityOrder, orderDto);
        } else {
            throw new IllegalArgumentException("Invalid order size.");
        }
        return securityOrderDao.save(securityOrder);
    }

    private void executeBuyOrder(SecurityOrder securityOrder, MarketOrderDto orderDto) {
        Account account = accountDao.findById(orderDto.getAccountId());
        Quote quote = quoteDao.findById(orderDto.getTicker());
        Double totalPrice = orderDto.getSize() * quote.getAskPrice();

        securityOrder.setStatus(StatusEnum.FILLED);
        if (totalPrice > account.getAmount()) {
            securityOrder.setNotes("Insufficient funds. Required buy-power:" + totalPrice);
            securityOrder.setStatus(StatusEnum.CANCELED);
        } else if (orderDto.getSize() > quote.getAskSize()){
            securityOrder.setNotes("Order size too large.");
            securityOrder.setStatus(StatusEnum.CANCELED);
        }
        account.setAmount(account.getAmount() - totalPrice);
        accountDao.save(account);
    }

    private void executeSellOrder(SecurityOrder securityOrder, MarketOrderDto orderDto) {
        Position position = positionDao.getByAccountIdAndTicker(orderDto.getAccountId(), orderDto.getTicker());
        Quote quote = quoteDao.findById(orderDto.getTicker());
        Account account = accountDao.findById(orderDto.getAccountId());
      Long absPosition = abs(position.getPosition());
      Long absOrderSize = abs(orderDto.getSize());
        Long absBidSize = abs(quote.getBidSize());

        securityOrder.setStatus(StatusEnum.FILLED);
        if (absPosition < absOrderSize) {
            securityOrder.setNotes("Insufficient position");
            securityOrder.setStatus(StatusEnum.CANCELED);
        } else if (absBidSize < absOrderSize){
            securityOrder.setNotes("Order size too large.");
            securityOrder.setStatus(StatusEnum.CANCELED);
        }
        account.setAmount(account.getAmount() - absOrderSize * quote.getBidPrice());
        accountDao.save(account);
    }
}