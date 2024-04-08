package edu.ntnu.idatt2105.rizzlet.validation.impl;

import edu.ntnu.idatt2105.rizzlet.validation.Enumerator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validator for the Enumerator constraint.
 * This validator ensures that an enumerator meets the specified
 * criteria defined in the application properties.
 */
public final class EnumeratorValidator implements ConstraintValidator<Enumerator, String> {

  private Enumerator enumerator;
  private Set<String> types;

  /**
   * Initializes the validator.
   *
   * @param enumerator The Enumerator annotation.
   */
  @Override
  public void initialize(Enumerator enumerator) {
    this.enumerator = enumerator;
    this.types = Arrays.stream(enumerator.value().getEnumConstants()).map(Enum::name).collect(Collectors.toSet());;
  }

  /**
   * Validates an enumerator.
   *
   * @param value The value to validate.
   * @param context The constraint validator context.
   * @return true if the value is valid, false otherwise.
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    String message = null;
    if ((value == null && !enumerator.nullable()) || (types != null && !types.contains(value))) {
      message = enumerator.message();
    }

    if (message != null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
