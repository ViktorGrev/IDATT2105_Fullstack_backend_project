package edu.ntnu.idatt2105.trivium.validation.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Annotation for validating usernames.
 * This annotation is used to mark fields or parameters that represent usernames,
 * ensuring that they meet specific validation criteria defined by the associated validator.
 */
@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {

  /**
   * Specifies the default error message that will be used when the validation fails.
   *
   * @return The default error message.
   */
  String message() default "Invalid username";

  /**
   * Specifies the groups to which this constraint belongs.
   *
   * @return An array of group classes.
   */
  Class<?>[] groups() default {};

  /**
   * Specifies additional metadata about the annotation.
   *
   * @return An array of payload classes.
   */
  Class<? extends Payload>[] payload() default {};

}
