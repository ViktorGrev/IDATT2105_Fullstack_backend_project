package edu.ntnu.idatt2105.trivium.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when attempting to create a user that already exists in the system.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User already exists")
public final class UserAlreadyExistsException extends RuntimeException {

  /**
   * Constructs a UserAlreadyExistsException with the default message.
   */
  public UserAlreadyExistsException() {
    super("User already exists");
  }
}
