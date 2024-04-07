package edu.ntnu.idatt2105.trivium.model.quiz.difficulty;

public enum DifficultyLevel {
    EASY,
    MEDIUM,
    HARD;

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