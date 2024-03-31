package edu.ntnu.idatt2105.trivium.dto.quiz.question;

import edu.ntnu.idatt2105.trivium.model.quiz.question.MultipleChoiceQuestion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class MultipleChoiceQuestionDTO extends QuestionDTO {

  @Size(min = MultipleChoiceQuestion.Config.OPTIONS_MIN_SIZE,
      max = MultipleChoiceQuestion.Config.OPTIONS_MAX_SIZE,
      message = "There must be between {min} and {max} multiple choice options")
  @NotNull(message = "A list of multiple choice options is required")
  private List<OptionDTO> options;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @EqualsAndHashCode(callSuper = false)
  public static class OptionDTO {

    private long id;

    @Size(min = MultipleChoiceQuestion.Option.Config.TEXT_MIN_LENGTH,
        max = MultipleChoiceQuestion.Option.Config.TEXT_MAX_LENGTH,
        message = "Option must be between {min} and {max} characters")
    @NotNull(message = "Option text is required")
    private String optionText;

    private boolean correct;
  }
}
