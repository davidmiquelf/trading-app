package ca.jrvs.apps.trading.service;

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

import static ca.jrvs.apps.trading.model.domain.SecurityOrder.*;
import static java.lang.Math.abs;


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

    private SecurityOrder executeBuyOrder(SecurityOrder securityOrder, MarketOrderDto orderDto) {
        Account account = accountDao.findById(orderDto.getAccountId());
        Quote quote = quoteDao.findById(orderDto.getTicker());
        Double totalPrice = orderDto.getSize() * quote.getAskPrice();

        securityOrder.setStatus(StatusEnum.FILLED);
        if (orderDto.getSize() > quote.getAskSize() || totalPrice > account.getAmount()) {
            securityOrder.setStatus(StatusEnum.CANCELED);
        }
        account.setAmount(account.getAmount() - totalPrice);
        accountDao.save(account);
        return securityOrder;
    }

    private SecurityOrder executeSellOrder(SecurityOrder securityOrder, MarketOrderDto orderDto) {
        Position position = positionDao.getByAccountIdAndTicker(orderDto.getAccountId(), orderDto.getTicker());
        Quote quote = quoteDao.findById(orderDto.getTicker());

        securityOrder.setStatus(StatusEnum.FILLED);
        Long resultPosition = abs(orderDto.getSize()) - abs(position.getPosition());
        if (abs(orderDto.getSize()) > abs(quote.getBidSize()) || resultPosition < 0) {
            securityOrder.setNotes("Insufficient fund.");
            securityOrder.setStatus(StatusEnum.CANCELED);
        }
        return securityOrder;
    }
}