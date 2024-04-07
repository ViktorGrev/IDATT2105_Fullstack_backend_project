package edu.ntnu.idatt2105.trivium.dto.quiz.answer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {

  @Min(value = 1, message = "Invalid question ID")
  private long question;

  @NotNull(message = "Answer is required")
  private Object answer;

}
