package edu.ntnu.idatt2105.trivium.model.quiz.featured;

import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeaturedQuiz {

  private Quiz quiz;
  private double avg_score;
  private long total_results;
  private double popularity_score;

}
