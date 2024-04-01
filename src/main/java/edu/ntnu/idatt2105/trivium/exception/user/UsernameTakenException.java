package edu.ntnu.idatt2105.trivium.exception.user;

/**
 * Exception thrown when attempting to create a user that already exists in the system.
 */
public final class UsernameTakenException extends RuntimeException {

  /**
   * Constructs a UserAlreadyExistsException with the default message.
   */
  public UsernameTakenException() {
    super("Username is taken");
  }
}
