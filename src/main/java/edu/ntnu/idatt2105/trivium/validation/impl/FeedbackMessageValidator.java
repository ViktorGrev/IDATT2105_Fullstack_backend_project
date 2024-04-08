package edu.ntnu.idatt2105.trivium.validation.impl;

import edu.ntnu.idatt2105.trivium.validation.FeedbackMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for the FeedbackMessage constraint.
 * This validator ensures that a feedback message meets the specified
 * criteria defined in the application properties.
 */
public final class FeedbackMessageValidator implements ConstraintValidator<FeedbackMessage, String> {

  /**
   * Initializes the validator.
   *
   * @param message The FeedbackMessage annotation.
   */
  @Override
  public void initialize(FeedbackMessage message) {

  }

  /**
   * Validates a feedback message.
   *
   * @param message The message to validate.
   * @param context The constraint validator context.
   * @return true if the message is valid, false otherwise.
   */
  @Override
  public boolean isValid(String feedbackMessage, ConstraintValidatorContext context) {
    String message = null;
    if (feedbackMessage == null) {
      message = "Message cannot be empty";
    } else if (feedbackMessage.length() < 5 || feedbackMessage.length() > 256) {
      message = "Message must be between 5 and 256 characters";
    }

    if (message != null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
