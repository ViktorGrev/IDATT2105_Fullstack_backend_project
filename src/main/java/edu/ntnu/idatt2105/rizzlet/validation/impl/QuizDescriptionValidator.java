package edu.ntnu.idatt2105.rizzlet.validation.impl;

import edu.ntnu.idatt2105.rizzlet.properties.QuizProperties;
import edu.ntnu.idatt2105.rizzlet.validation.QuizDescription;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for the QuizTitle constraint.
 * This validator ensures that a quiz title meets the specified
 * criteria defined in the application properties.
 */
public final class QuizDescriptionValidator implements ConstraintValidator<QuizDescription, String> {

  /**
   * Initializes the validator.
   *
   * @param description The QuizDescription annotation.
   */
  @Override
  public void initialize(QuizDescription description) {

  }

  /**
   * Validates a quiz title.
   *
   * @param title The title to validate.
   * @param context The constraint validator context.
   * @return true if the title is valid, false otherwise.
   */
  @Override
  public boolean isValid(String description, ConstraintValidatorContext context) {
    String message = null;
    if (description != null && description.length() > QuizProperties.DESC_LEN_MAX) {
      message = QuizProperties.DESC_LEN_MSG;
    }

    if (message != null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
