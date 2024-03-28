package edu.ntnu.idatt2105.trivium.dto.security;

import edu.ntnu.idatt2105.trivium.model.user.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Represents a request for a user containing the credentials.
 */
@Data
public class CredentialsRequest {

  @Size(
      min = User.Config.MIN_USERNAME_LENGTH,
      max = User.Config.MAX_USERNAME_LENGTH,
      message = "Username must be between {min} and {max} characters")
  @Pattern(
      regexp = User.Config.USERNAME_REGEX,
      message = User.Config.USERNAME_REGEX_MSG)
  @NotNull(message = "Username is required")
  private String username;

  @Size(
      min = User.Config.MIN_PASSWORD_LENGTH,
      max = User.Config.MAX_PASSWORD_LENGTH,
      message = "Password must be between {min} and {max} characters")
  @Pattern(
      regexp = User.Config.PASSWORD_REGEX,
      message = User.Config.PASSWORD_REGEX_MSG)
  @NotNull(message = "Password is required")
  private String password;
}
