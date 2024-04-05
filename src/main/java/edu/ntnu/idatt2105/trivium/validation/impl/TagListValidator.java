package edu.ntnu.idatt2105.trivium.validation.impl;

import edu.ntnu.idatt2105.trivium.properties.QuizProperties;
import edu.ntnu.idatt2105.trivium.validation.TagList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

/**
 * Validator for the TagList constraint.
 * This validator ensures that a tag list meets the specified
 * criteria defined in the application properties.
 */
public final class TagListValidator implements ConstraintValidator<TagList, List<String>> {

  /**
   * Initializes the validator.
   *
   * @param list The TagList annotation.
   */
  @Override
  public void initialize(TagList list) {

  }

  /**
   * Validates a tag list.
   *
   * @param list The list to validate.
   * @param context The constraint validator context.
   * @return true if the list is valid, false otherwise.
   */
  @Override
  public boolean isValid(List<String> list, ConstraintValidatorContext context) {
    String message = null;
    if (list.size() > QuizProperties.TAG_LIST_LEN_MAX) {
      message = QuizProperties.TAG_LIST_LEN_MSG;
    }

    if (message != null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
