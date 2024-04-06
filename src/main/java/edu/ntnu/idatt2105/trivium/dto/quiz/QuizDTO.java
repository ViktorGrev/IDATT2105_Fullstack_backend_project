package edu.ntnu.idatt2105.trivium.dto.quiz;

import edu.ntnu.idatt2105.trivium.dto.quiz.question.QuestionDTO;
import edu.ntnu.idatt2105.trivium.dto.user.UserDTO;
import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
public class QuizDTO {

  @Nullable
  private long id;
  private String title;
  private String description;
  private Quiz.Category category;
  private List<String> tags;
  private List<QuestionDTO> questions;
  private boolean random;
  private UserDTO creator;
  private List<UserDTO> coAuthors;

}
