package edu.ntnu.idatt2105.rizzlet.dto.quiz.question;

import edu.ntnu.idatt2105.rizzlet.validation.OptionList;
import edu.ntnu.idatt2105.rizzlet.validation.OptionText;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class MultipleChoiceQuestionDTO extends QuestionDTO {

  @OptionList
  private List<@Valid OptionDTO> options;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @EqualsAndHashCode(callSuper = false)
  public static class OptionDTO {

    private long id;

    @OptionText
    private String optionText;

    private boolean correct;
  }
}
