package edu.ntnu.idatt2105.trivium.exception.quiz.result;

/**
 * Exception thrown when attempting to retrieve a result that does not exist.
 */
public final class ResultNotFoundException extends RuntimeException {

  /**
   * Constructs a ResultNotFoundException with the default message.
   */
  public ResultNotFoundException() {
    super("Result not found");
  }
}
