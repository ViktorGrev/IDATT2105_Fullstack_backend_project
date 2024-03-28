package edu.ntnu.idatt2105.trivium.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

  private long id;
  private String username;
  @JsonIgnore
  private String password;

  public User() {

  }

  public User(@JsonProperty("username") String username, String password) {
    this.username = username;
    this.password = password;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public static class Config {

    // Username validation
    public static final int MIN_USERNAME_LENGTH = 4;
    public static final int MAX_USERNAME_LENGTH = 16;
    public static final String USERNAME_REGEX = "[\\w]+$";
    public static final String USERNAME_REGEX_MSG = "Username must contain only letters, digits and underscores";

    // Password validation
    public static final int MIN_PASSWORD_LENGTH = 4;
    public static final int MAX_PASSWORD_LENGTH = 16;
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\w]+$";
    public static final String PASSWORD_REGEX_MSG = "Password must contain at least one lowercase letter, " +
        "one uppercase letter and one digit";
  }
}
