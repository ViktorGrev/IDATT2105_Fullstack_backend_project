package edu.ntnu.idatt2105.trivium.validation.impl;

import edu.ntnu.idatt2105.trivium.properties.QuizProperties;
import edu.ntnu.idatt2105.trivium.validation.CoAuthorList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

/**
 * Validator for the CoAuthorList constraint.
 * This validator ensures that a co-author list meets the specified
 * criteria defined in the application properties.
 */
public final class CoAuthorListValidator implements ConstraintValidator<CoAuthorList, List<String>> {

  /**
   * Initializes the validator.
   *
   * @param list The TagList annotation.
   */
  @Override
  public void initialize(CoAuthorList list) {

  }

  /**
   * Validates a co-author list.
   *
   * @param list The list to validate.
   * @param context The constraint validator context.
   * @return true if the list is valid, false otherwise.
   */
  @Override
  public boolean isValid(List<String> list, ConstraintValidatorContext context) {
    String message = null;
    if (list.size() > QuizProperties.CO_AUTH_LIST_LEN_MAX) {
      message = QuizProperties.CO_AUTH_LIST_LEN_MSG;
    }

    if (message != null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
