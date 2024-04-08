package edu.ntnu.idatt2105.trivium.dto.user;

import edu.ntnu.idatt2105.trivium.validation.FeedbackMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SendFeedbackDTO {

  @Email
  @NotNull(message = "Email is required")
  private String email;

  @FeedbackMessage
  private String message;
}
