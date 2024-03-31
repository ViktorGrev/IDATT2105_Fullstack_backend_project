package edu.ntnu.idatt2105.trivium.model.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "username", length = Config.MAX_USERNAME_LENGTH, unique = true, nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

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
