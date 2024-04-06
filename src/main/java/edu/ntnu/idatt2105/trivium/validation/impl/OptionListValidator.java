package edu.ntnu.idatt2105.trivium.validation.impl;

import edu.ntnu.idatt2105.trivium.dto.quiz.question.MultipleChoiceQuestionDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.question.QuestionDTO;
import edu.ntnu.idatt2105.trivium.properties.QuizProperties;
import edu.ntnu.idatt2105.trivium.validation.OptionList;
import edu.ntnu.idatt2105.trivium.validation.QuestionList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

/**
 * Validator for the OptionList constraint.
 * This validator ensures that an option list meets the specified
 * criteria defined in the application properties.
 */
public final class OptionListValidator implements ConstraintValidator<OptionList, List<MultipleChoiceQuestionDTO.OptionDTO>> {

  /**
   * Initializes the validator.
   *
   * @param list The OptionList annotation.
   */
  @Override
  public void initialize(OptionList list) {

  }

  /**
   * Validates a list.
   *
   * @param list The list to validate.
   * @param context The constraint validator context.
   * @return true if the list is valid, false otherwise.
   */
  @Override
  public boolean isValid(List<MultipleChoiceQuestionDTO.OptionDTO> list, ConstraintValidatorContext context) {
    String message = null;
    if (list == null) {
      message = QuizProperties.OPTION_LIST_EMPTY;
    } else if (list.size() < QuizProperties.OPTION_LIST_LEN_MIN || list.size() > QuizProperties.OPTION_LIST_LEN_MAX) {
      message = QuizProperties.OPTION_LIST_LEN_MSG;
    }

    if (message != null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
