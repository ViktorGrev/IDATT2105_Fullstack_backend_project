package edu.ntnu.idatt2105.trivium.exception.user;

/**
 * Exception thrown when attempting to retrieve a user that does not exist in the system.
 */
public final class UserNotFoundException extends RuntimeException {

  /**
   * Constructs a UserNotFoundException with the default message.
   */
  public UserNotFoundException() {
    super("User not found");
  }

  /**
   * Constructs a UserNotFoundException with the default message.
   */
  public UserNotFoundException(String string) {
    super(string);
  }
}
