package edu.ntnu.idatt2105.trivium.validation.impl;

import edu.ntnu.idatt2105.trivium.properties.QuizProperties;
import edu.ntnu.idatt2105.trivium.validation.OptionText;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for the OptionText constraint.
 * This validator ensures that an option text meets the specified
 * criteria defined in the application properties.
 */
public final class OptionTextValidator implements ConstraintValidator<OptionText, String> {

  /**
   * Initializes the validator.
   *
   * @param text The OptionText annotation.
   */
  @Override
  public void initialize(OptionText text) {

  }

  /**
   * Validates a text.
   *
   * @param text The text to validate.
   * @param context The constraint validator context.
   * @return true if the text is valid, false otherwise.
   */
  @Override
  public boolean isValid(String text, ConstraintValidatorContext context) {
    String message = null;
    if (text == null) {
      message = QuizProperties.OPTION_TEXT_EMPTY;
    } else if (text.length() < QuizProperties.OPTION_TEXT_LEN_MIN || text.length() > QuizProperties.OPTION_TEXT_LEN_MAX) {
      message = QuizProperties.OPTION_TEXT_LEN_MSG;
    }

    if (message != null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
