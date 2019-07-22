package ca.jrvs.apps.trading.model.domain;

public class Account implements Entity<Long> {

  private Long id;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

}
