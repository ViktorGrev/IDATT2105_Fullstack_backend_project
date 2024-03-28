package edu.ntnu.idatt2105.trivium.exception.auth;

/**
 * Exception thrown when authentication fails due to invalid credentials.
 */
public class InvalidCredentialsException extends RuntimeException {

  /**
   * Constructs an InvalidCredentialsException with the default message.
   */
  public InvalidCredentialsException() {
    super("Invalid credentials");
  }
}
