package edu.ntnu.idatt2105.trivium.validation.user;

import edu.ntnu.idatt2105.trivium.properties.UserProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

/**
 * Validator for the Password constraint.
 * This validator ensures that a password meets the specified
 * criteria defined in the application properties.
 */
public final class PasswordValidator implements ConstraintValidator<Password, String> {

  @Autowired
  private UserProperties properties;

  private Pattern pattern;

  /**
   * Initializes the validator.
   *
   * @param password The Password annotation.
   */
  @Override
  public void initialize(Password password) {
    if (pattern == null) {
      pattern = Pattern.compile(properties.PASS_REGEX);
    }
  }

  /**
   * Validates a password.
   *
   * @param password The password to validate.
   * @param context The constraint validator context.
   * @return true if the password is valid, false otherwise.
   */
  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    String message = null;
    if (password == null) {
      message = properties.PASS_EMPTY;
    } else if (password.length() < properties.PASS_LEN_MIN || password.length() > properties.PASS_LEN_MAX) {
      message = properties.PASS_LEN_MSG;
    } else if (!pattern.matcher(password).matches()) {
      message = properties.PASS_REGEX_MSG;
    }

    if (message != null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
