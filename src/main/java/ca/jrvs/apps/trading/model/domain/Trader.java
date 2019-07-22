package ca.jrvs.apps.trading.model.domain;

import java.time.LocalDate;

public class Trader implements Entity<Long> {

  private Long id;
  private String firstName;
  private String lastName;
  private LocalDate dateOfBirth;
  private String Country;
  private String email;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getCountry() {
    return Country;
  }

  public void setCountry(String country) {
    Country = country;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
