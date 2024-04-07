package edu.ntnu.idatt2105.trivium.model.quiz.difficulty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizDifficulty {

  private double averageScore;
  private DifficultyLevel level;

}
