package edu.ntnu.idatt2105.trivium.dto.quiz.answer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnswerDTO {

  @Min(value = 1, message = "Invalid question ID")
  private long question;

  @NotNull(message = "Answer is required")
  private Object answer;

}
