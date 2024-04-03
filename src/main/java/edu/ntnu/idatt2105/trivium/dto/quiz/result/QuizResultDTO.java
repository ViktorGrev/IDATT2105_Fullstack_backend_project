package edu.ntnu.idatt2105.trivium.dto.quiz.result;

import edu.ntnu.idatt2105.trivium.dto.quiz.QuizDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.answer.AnswerDTO;
import edu.ntnu.idatt2105.trivium.dto.user.UserDTO;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class QuizResultDTO {

  private long id;
  private UserDTO user;
  private int score;
  private Timestamp timestamp;
  private List<AnswerDTO> answers;
  private QuizDTO quiz;
}
