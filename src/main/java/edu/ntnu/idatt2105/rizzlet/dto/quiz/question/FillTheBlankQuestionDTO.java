package edu.ntnu.idatt2105.rizzlet.dto.quiz.question;

import edu.ntnu.idatt2105.rizzlet.validation.FillTheBlankSolution;
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

  @FillTheBlankSolution
  private String solution;
}
