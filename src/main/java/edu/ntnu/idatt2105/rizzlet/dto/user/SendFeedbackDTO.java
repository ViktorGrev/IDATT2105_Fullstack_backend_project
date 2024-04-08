package edu.ntnu.idatt2105.rizzlet.dto.user;

import edu.ntnu.idatt2105.rizzlet.validation.FeedbackMessage;
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
