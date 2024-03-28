package edu.ntnu.idatt2105.trivium.model;

import lombok.Data;

@Data
public class User {

  private long id;
  private String username;
  private String password;

  public User() {

  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
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
