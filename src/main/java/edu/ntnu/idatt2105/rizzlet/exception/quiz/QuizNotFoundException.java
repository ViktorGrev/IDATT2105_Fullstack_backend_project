package edu.ntnu.idatt2105.rizzlet.exception.quiz;

/**
 * Exception thrown when attempting to retrieve a quiz that does not exist.
 */
public final class QuizNotFoundException extends RuntimeException {

  /**
   * Constructs a QuizNotFoundException with the default message.
   */
  public QuizNotFoundException() {
    super("Quiz not found");
  }
}
