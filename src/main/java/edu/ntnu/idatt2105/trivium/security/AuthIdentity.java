package edu.ntnu.idatt2105.trivium.security;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents the identity of an authenticated user.
 */
@Data
@AllArgsConstructor
public class AuthIdentity {

  /**
   * The ID of the authenticated user.
   */
  private long id;

  /**
   * The role of the authenticated user.
   */
  private String role;
}
