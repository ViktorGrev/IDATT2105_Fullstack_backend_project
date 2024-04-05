package edu.ntnu.idatt2105.trivium.properties;

import org.springframework.stereotype.Component;

/**
 * Configuration properties related to user validation.
 * Provides constants for username and password validation.
 */
@Component
public final class UserProperties {

  // Username
  public static final String NAME_EMPTY = "Username is required";
  public static final int NAME_LEN_MIN = 4;
  public static final int NAME_LEN_MAX = 16;
  public static final String NAME_LEN_MSG = "Username must be between " + NAME_LEN_MIN + " and " + NAME_LEN_MAX + " characters";
  public static final String NAME_REGEX = "[\\w]+$";
  public static final String NAME_REGEX_MSG = "Username must contain only letters, digits and underscores";

  // Password
  public static final String PASS_EMPTY = "Password is required";
  public static final int PASS_LEN_MIN = 4;
  public static final int PASS_LEN_MAX = 16;
  public static final String PASS_LEN_MSG = "Password must be between " + PASS_LEN_MIN + " and " + PASS_LEN_MAX + " characters";
  public static final String PASS_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\w]+$";
  public static final String PASS_REGEX_MSG = "Password must contain at least one lowercase letter one uppercase letter and one digit";

}
