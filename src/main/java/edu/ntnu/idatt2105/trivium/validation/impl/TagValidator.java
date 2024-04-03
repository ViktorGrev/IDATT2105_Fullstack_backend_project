package edu.ntnu.idatt2105.trivium.validation.impl;

import edu.ntnu.idatt2105.trivium.properties.QuizProperties;
import edu.ntnu.idatt2105.trivium.validation.Tag;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

/**
 * Validator for the Tag constraint.
 * This validator ensures that a tag meets the specified
 * criteria defined in the application properties.
 */
public final class TagValidator implements ConstraintValidator<Tag, String> {

  @Autowired
  private QuizProperties properties;

  private Pattern pattern;

  /**
   * Initializes the validator.
   *
   * @param username The Username annotation.
   */
  @Override
  public void initialize(Tag tag) {
    if (pattern == null) {
      pattern = Pattern.compile(properties.TAG_REGEX);
    }
  }

  /**
   * Validates a tag.
   *
   * @param tag The tag to validate.
   * @param context The constraint validator context.
   * @return true if the tag is valid, false otherwise.
   */
  @Override
  public boolean isValid(String tag, ConstraintValidatorContext context) {
    String message = null;
    if (tag == null) {
      message = properties.TAG_EMPTY;
    } else if (tag.length() < properties.TAG_LEN_MIN || tag.length() > properties.TAG_LEN_MAX) {
      message = properties.TAG_LEN_MSG;
    } else if (!pattern.matcher(tag).matches()) {
      message = properties.TAG_REGEX_MSG;
    }

    if (message != null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
