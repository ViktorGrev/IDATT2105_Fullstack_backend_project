package edu.ntnu.idatt2105.trivium.validation.impl;

import edu.ntnu.idatt2105.trivium.properties.UserProperties;
import edu.ntnu.idatt2105.trivium.validation.Username;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

/**
 * Validator for the Username constraint.
 * This validator ensures that a username meets the specified
 * criteria defined in the application properties.
 */
public final class UsernameValidator implements ConstraintValidator<Username, String> {

  @Autowired
  private UserProperties properties;

  private Pattern pattern;

  /**
   * Initializes the validator.
   *
   * @param username The Username annotation.
   */
  @Override
  public void initialize(Username username) {
    if (pattern == null) {
      pattern = Pattern.compile(properties.NAME_REGEX);
    }
  }

  /**
   * Validates a username.
   *
   * @param username The username to validate.
   * @param context The constraint validator context.
   * @return true if the username is valid, false otherwise.
   */
  @Override
  public boolean isValid(String username, ConstraintValidatorContext context) {
    String message = null;
    if (username == null) {
      message = properties.NAME_EMPTY;
    } else if (username.length() < properties.NAME_LEN_MIN || username.length() > properties.NAME_LEN_MAX) {
      message = properties.NAME_LEN_MSG;
    } else if (!pattern.matcher(username).matches()) {
      message = properties.NAME_REGEX_MSG;
    }

    if (message != null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
