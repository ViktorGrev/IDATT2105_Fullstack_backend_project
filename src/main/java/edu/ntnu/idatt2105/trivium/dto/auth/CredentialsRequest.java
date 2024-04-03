package edu.ntnu.idatt2105.trivium.dto.auth;

import edu.ntnu.idatt2105.trivium.validation.Password;
import edu.ntnu.idatt2105.trivium.validation.Username;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a request for a user containing the credentials.
 */
@Data
@AllArgsConstructor
public class CredentialsRequest {

  @Username
  private String username;

  @Password
  private String password;
}
