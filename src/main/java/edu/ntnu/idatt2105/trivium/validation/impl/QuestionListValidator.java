package edu.ntnu.idatt2105.trivium.validation.impl;

import edu.ntnu.idatt2105.trivium.dto.quiz.question.QuestionDTO;
import edu.ntnu.idatt2105.trivium.properties.QuizProperties;
import edu.ntnu.idatt2105.trivium.validation.QuestionList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

/**
 * Validator for the QuestionList constraint.
 * This validator ensures that a question list meets the specified
 * criteria defined in the application properties.
 */
public final class QuestionListValidator implements ConstraintValidator<QuestionList, List<QuestionDTO>> {

  /**
   * Initializes the validator.
   *
   * @param list The QuestionList annotation.
   */
  @Override
  public void initialize(QuestionList list) {

  }

  /**
   * Validates a list.
   *
   * @param list The list to validate.
   * @param context The constraint validator context.
   * @return true if the list is valid, false otherwise.
   */
  @Override
  public boolean isValid(List<QuestionDTO> list, ConstraintValidatorContext context) {
    String message = null;
    if (list == null) {
      message = QuizProperties.QUESTION_LIST_EMPTY;
    } else if (list.size() < QuizProperties.QUESTION_LIST_LEN_MIN || list.size() > QuizProperties.QUESTION_LIST_LEN_MAX) {
      message = QuizProperties.QUESTION_LIST_LEN_MSG;
    }

    if (message != null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
