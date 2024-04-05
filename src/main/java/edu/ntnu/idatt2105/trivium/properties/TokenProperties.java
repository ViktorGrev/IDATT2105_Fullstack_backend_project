package edu.ntnu.idatt2105.trivium.properties;

import org.springframework.stereotype.Component;

/**
 * Configuration properties for JWT (JSON Web Token) generation.
 */
@Component
public final class TokenProperties {

  /**
   * The secret key used for JWT generation.
   */
  public static final String SECRET = "topsecretkey";

  /**
   * The duration of the JWT token validity in minutes.
   */
  public static final int DURATION = 30;

}
