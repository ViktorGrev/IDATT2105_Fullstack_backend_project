package edu.ntnu.idatt2105.trivium.model.quiz.difficulty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a {@link QuizDifficulty} entity.
 */
@Data
@AllArgsConstructor
public class QuizDifficulty {

  private double averageScore;
  private DifficultyLevel level;

}
