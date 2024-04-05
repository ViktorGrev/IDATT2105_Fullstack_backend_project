package edu.ntnu.idatt2105.trivium.security;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents the identity of an authenticated user.
 */
@Data
@AllArgsConstructor
public class AuthIdentity {

  private long id;
  private String role;
}
