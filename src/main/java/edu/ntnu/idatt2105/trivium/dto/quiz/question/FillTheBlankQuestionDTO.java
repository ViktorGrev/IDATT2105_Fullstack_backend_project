package edu.ntnu.idatt2105.trivium.dto.quiz.question;

import edu.ntnu.idatt2105.trivium.model.quiz.question.FillTheBlankQuestion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class FillTheBlankQuestionDTO extends QuestionDTO {

  @Size(min = FillTheBlankQuestion.Config.SOLUTION_MIN_LENGTH,
      max = FillTheBlankQuestion.Config.SOLUTION_MAX_LENGTH,
      message = "Solution must be between {min} and {max} characters")
  @NotNull(message = "Solution is required")
  private String solution;
}
