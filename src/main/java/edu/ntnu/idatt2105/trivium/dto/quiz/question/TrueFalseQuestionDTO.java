package edu.ntnu.idatt2105.trivium.dto.quiz.question;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class TrueFalseQuestionDTO extends QuestionDTO {

  @JsonProperty("true")
  private boolean isTrue;

}
