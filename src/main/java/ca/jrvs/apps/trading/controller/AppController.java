package ca.jrvs.apps.trading.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/")
public class AppController {

  @GetMapping(path = "/health")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public String healthCheck() {
    return "Healthy!";
  }

}
