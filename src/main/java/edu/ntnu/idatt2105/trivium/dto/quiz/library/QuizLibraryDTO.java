package edu.ntnu.idatt2105.trivium.dto.quiz.library;

import edu.ntnu.idatt2105.trivium.dto.quiz.QuizDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizLibraryDTO {

  private List<QuizDTO> created;
  private List<QuizDTO> coAuthored;

}
