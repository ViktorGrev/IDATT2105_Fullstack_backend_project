package edu.ntnu.idatt2105.trivium.validation.impl;

import edu.ntnu.idatt2105.trivium.properties.QuizProperties;
import edu.ntnu.idatt2105.trivium.validation.QuizTitle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for the QuizTitle constraint.
 * This validator ensures that a quiz title meets the specified
 * criteria defined in the application properties.
 */
public final class QuizTitleValidator implements ConstraintValidator<QuizTitle, String> {

  /**
   * Initializes the validator.
   *
   * @param title The QuizTitle annotation.
   */
  @Override
  public void initialize(QuizTitle title) {

  }

  /**
   * Validates a quiz title.
   *
   * @param title The title to validate.
   * @param context The constraint validator context.
   * @return true if the title is valid, false otherwise.
   */
  @Override
  public boolean isValid(String title, ConstraintValidatorContext context) {
    String message = null;
    if (title == null) {
      message = QuizProperties.TITLE_EMPTY;
    } else if (title.length() < QuizProperties.TITLE_LEN_MIN || title.length() > QuizProperties.TITLE_LEN_MAX) {
      message = QuizProperties.TITLE_LEN_MSG;
    }

    if (message != null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
