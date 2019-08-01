package ca.jrvs.apps.trading.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/")
public class AppController {

  @PutMapping(path = "/health")
  @ResponseStatus(HttpStatus.OK)
  public void healthCheck() {
  }

}
