package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "country",
        "dob",
        "email",
        "firstName",
        "id",
        "lastName"
})
public class Trader implements Entity<Long>{

  @JsonProperty("country")
  private String country;
  @JsonProperty("dob")
  private LocalDate dob;
  @JsonProperty("email")
  private String email;
  @JsonProperty("firstName")
  private String firstName;
  @JsonProperty("id")
  private Long id;
  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("country")
  public String getCountry() {
    return country;
  }

  @JsonProperty("country")
  public void setCountry(String country) {
    this.country = country;
  }

  public Trader withCountry(String country) {
    this.country = country;
    return this;
  }

  @JsonProperty("dob")
  public LocalDate getDob() {
    return dob;
  }

  @JsonProperty("dob")
  public void setDob(LocalDate dob) {
    this.dob = dob;
  }

  public Trader withDob(LocalDate dob) {
    this.dob = dob;
    return this;
  }

  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  @JsonProperty("email")
  public void setEmail(String email) {
    this.email = email;
  }

  public Trader withEmail(String email) {
    this.email = email;
    return this;
  }

  @JsonProperty("firstName")
  public String getFirstName() {
    return firstName;
  }

  @JsonProperty("firstName")
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Trader withFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(Long id) {
    this.id = id;
  }

  public Trader withId(Long id) {
    this.id = id;
    return this;
  }

  @JsonProperty("lastName")
  public String getLastName() {
    return lastName;
  }

  @JsonProperty("lastName")
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Trader withLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

}