package edu.ntnu.idatt2105.trivium.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when authentication fails due to invalid credentials.
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid credentials")
public class InvalidCredentialsException extends RuntimeException {

  /**
   * Constructs an InvalidCredentialsException with the default message.
   */
  public InvalidCredentialsException() {
    super("Invalid credentials");
  }
}
