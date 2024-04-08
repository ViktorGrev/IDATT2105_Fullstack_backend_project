package edu.ntnu.idatt2105.rizzlet.dto.user;

import lombok.Data;

@Data
public class FeedbackDTO {

  private String email;
  private String message;
  private UserDTO sender;
}
