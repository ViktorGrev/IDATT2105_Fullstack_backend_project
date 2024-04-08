package edu.ntnu.idatt2105.rizzlet.exception.quiz.answer;

/**
 * Exception thrown when attempting to answer a question with the wrong answer format.
 */
public final class InvalidAnswerFormatException extends RuntimeException {

  /**
   * Constructs a InvalidAnswerFormatException with the default message.
   */
  public InvalidAnswerFormatException(long questionId) {
    super("Invalid answer format for question " + questionId);
  }

  /**
   * Constructs a InvalidAnswerFormatException with the default message.
   */
  public InvalidAnswerFormatException(String message) {
    super(message);
  }
}
