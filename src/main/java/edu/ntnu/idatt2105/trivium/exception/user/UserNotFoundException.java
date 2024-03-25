package edu.ntnu.idatt2105.trivium.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when attempting to retrieve a user that does not exist in the system.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found")
public final class UserNotFoundException extends RuntimeException {

  /**
   * Constructs a UserNotFoundException with the default message.
   */
  public UserNotFoundException() {
    super("User not found");
  }
}
