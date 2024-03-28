package edu.ntnu.idatt2105.trivium.exception.user;

/**
 * Exception thrown when attempting to create a user that already exists in the system.
 */
public final class UserAlreadyExistsException extends RuntimeException {

  /**
   * Constructs a UserAlreadyExistsException with the default message.
   */
  public UserAlreadyExistsException() {
    super("User already exists");
  }
}
