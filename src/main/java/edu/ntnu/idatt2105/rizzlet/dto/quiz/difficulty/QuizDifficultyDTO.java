package edu.ntnu.idatt2105.rizzlet.dto.quiz.difficulty;

import edu.ntnu.idatt2105.rizzlet.model.quiz.difficulty.DifficultyLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDifficultyDTO {

  private double averageScore;
  private DifficultyLevel level;
}
