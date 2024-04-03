package edu.ntnu.idatt2105.trivium.validation.impl;

import edu.ntnu.idatt2105.trivium.properties.QuizProperties;
import edu.ntnu.idatt2105.trivium.validation.QuestionText;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Validator for the QuestionText constraint.
 * This validator ensures that a question text meets the specified
 * criteria defined in the application properties.
 */
public final class QuestionTextValidator implements ConstraintValidator<QuestionText, String> {

  @Autowired
  private QuizProperties properties;

  /**
   * Initializes the validator.
   *
   * @param username The Username annotation.
   */
  @Override
  public void initialize(QuestionText text) {

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
      message = properties.QUESTION_TEXT_EMPTY;
    } else if (text.length() < properties.QUESTION_TEXT_LEN_MIN || text.length() > properties.QUESTION_TEXT_LEN_MAX) {
      message = properties.QUESTION_TEXT_LEN_MSG;
    }

    if (message != null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
