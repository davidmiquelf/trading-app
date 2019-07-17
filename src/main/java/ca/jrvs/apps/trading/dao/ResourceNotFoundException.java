package ca.jrvs.apps.trading.dao;

public class ResourceNotFoundException extends Exception {

  public ResourceNotFoundException(Exception e) {
    super(e.getMessage());
  }


}
