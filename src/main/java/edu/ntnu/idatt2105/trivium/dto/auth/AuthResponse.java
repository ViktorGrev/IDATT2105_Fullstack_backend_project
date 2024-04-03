package edu.ntnu.idatt2105.trivium.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents an authentication response containing a token.
 */
@Data
@AllArgsConstructor
public class AuthResponse {

  private String token;
}
