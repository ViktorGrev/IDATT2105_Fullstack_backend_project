package edu.ntnu.idatt2105.trivium.model.quiz.library;

import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuizLibrary {

  private List<Quiz> created;
  private List<Quiz> coAuthored;

}
