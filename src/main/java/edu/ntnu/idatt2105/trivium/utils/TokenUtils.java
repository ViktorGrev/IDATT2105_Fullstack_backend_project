package edu.ntnu.idatt2105.trivium.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.properties.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

/**
 * Utility class for working with JWT (JSON Web Token) generation and validation.
 */
@Component
public final class TokenUtils {

  public static String SECRET;
  private static Duration TOKEN_DURATION;

  /**
   * Generates a JWT (JSON Web Token) for the given user.
   *
   * @param user The user for whom the token is generated.
   * @return The generated JWT token as a string.
   */
  public static String generateToken(final User user) {
    final Instant now = Instant.now();
    final Algorithm hmac512 = Algorithm.HMAC512(SECRET);;
    return JWT.create()
        .withSubject(String.valueOf(user.getId()))
        .withClaim("user_role", user.getRole().name())
        .withIssuer("trivium")
        .withIssuedAt(now)
        .withExpiresAt(now.plusMillis(TOKEN_DURATION.toMillis()))
        .sign(hmac512);
  }

  /**
   * Sets the properties for the JWT generation.
   *
   * @param properties The properties containing the secret and token duration.
   */
  @Autowired
  public void setProperties(TokenProperties properties) {
    SECRET = properties.SECRET;
    TOKEN_DURATION = Duration.ofMinutes(properties.DURATION);
  }
}
