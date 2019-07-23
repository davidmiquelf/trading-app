package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDate;

@JsonPropertyOrder({
    "country",
    "dob",
    "email",
    "firstName",
    "id",
    "lastName"
})
public class Trader implements Entity<Long> {

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

  @JsonProperty("dob")
  public LocalDate getDob() {
    return dob;
  }

  @JsonProperty("dob")
  public void setDob(LocalDate dob) {
    this.dob = dob;
  }

  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  @JsonProperty("email")
  public void setEmail(String email) {
    this.email = email;
  }

  @JsonProperty("firstName")
  public String getFirstName() {
    return firstName;
  }

  @JsonProperty("firstName")
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(Long id) {
    this.id = id;
  }

  @JsonProperty("lastName")
  public String getLastName() {
    return lastName;
  }

  @JsonProperty("lastName")
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

}
