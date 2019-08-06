package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import ca.jrvs.apps.trading.service.FundTransferService;
import ca.jrvs.apps.trading.service.RegisterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/trader")
public class TraderController {

  private RegisterService registerService;
  private FundTransferService fundTransferService;

  @Autowired
  public TraderController(RegisterService registerService,
      FundTransferService fundTransferService) {
    this.registerService = registerService;
    this.fundTransferService = fundTransferService;
  }

  @DeleteMapping(path = "/traderId/{traderId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteTrader(@PathVariable String traderId) {
    try {
      registerService.deleteTraderById(Integer.parseInt(traderId));
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @PostMapping(path = "/")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public TraderAccountView postTraderDTO(@RequestBody Trader trader) {
    try {
      return registerService.createTraderAndAccount(trader);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @PostMapping(path =
      "/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public TraderAccountView postTrader(
      @PathVariable String firstname,
      @PathVariable String lastname,
      @PathVariable String dob,
      @PathVariable String country,
      @PathVariable String email) {
    try {
      Trader trader = new Trader()
          .withFirstName(firstname)
          .withLastName(lastname)
          .withDob(dob)
          .withEmail(country)
          .withCountry(email);
      return registerService.createTraderAndAccount(trader);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @PutMapping(path = "/deposit/accountId/{accountId}/amount/{amount}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Account putDeposit(@PathVariable String accountId, @PathVariable String amount) {
    try {
      return fundTransferService.deposit(Integer.parseInt(accountId), Double.parseDouble(amount));
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @PutMapping(path = "/withdraw/accountId/{accountId}/amount/{amount}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Account putWithdraw(@PathVariable String accountId, @PathVariable String amount) {
    try {
      return fundTransferService.withdraw(Integer.parseInt(accountId), Double.parseDouble(amount));
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @GetMapping(path = "/traderId/{traderId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<TraderAccountView> viewAccount(@PathVariable String traderId) {
    try {
      return registerService.viewAccounts(Integer.parseInt(traderId));
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

}
