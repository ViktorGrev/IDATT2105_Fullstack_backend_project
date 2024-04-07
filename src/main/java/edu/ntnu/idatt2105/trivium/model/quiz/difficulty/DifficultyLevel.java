package edu.ntnu.idatt2105.trivium.model.quiz.difficulty;

/**
 * Enum representing the difficulty levels of a quiz.
 */
public enum DifficultyLevel {
  EASY,
  MEDIUM,
  HARD;

  /**
   * Converts a percentage to a difficulty level.
   *
   * @param percentage The percentage representing the difficulty.
   * @return The corresponding DifficultyLevel.
   */
  public static DifficultyLevel fromPercentage(double percentage) {
    if (percentage < 25) {
      return HARD;
    } else if (percentage < 50) {
      return MEDIUM;
    } else {
      return EASY;
    }
  }
}