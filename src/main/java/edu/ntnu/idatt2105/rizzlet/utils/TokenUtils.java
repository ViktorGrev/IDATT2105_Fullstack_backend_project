package edu.ntnu.idatt2105.rizzlet.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import edu.ntnu.idatt2105.rizzlet.model.user.User;
import edu.ntnu.idatt2105.rizzlet.properties.TokenProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

/**
 * Utility class for working with JWT (JSON Web Token) generation and validation.
 */
@Component
public final class TokenUtils {

  /**
   * Generates a JWT (JSON Web Token) for the given user.
   *
   * @param user The user for whom the token is generated.
   * @return The generated JWT token as a string.
   */
  public static String generateToken(final User user) {
    final Instant now = Instant.now();
    final Algorithm hmac512 = Algorithm.HMAC512(TokenProperties.SECRET);;
    return JWT.create()
        .withSubject(String.valueOf(user.getId()))
        .withClaim("user_role", user.getRole().name())
        .withIssuer("trivium")
        .withIssuedAt(now)
        .withExpiresAt(now.plusMillis(Duration.ofMinutes(TokenProperties.DURATION).toMillis()))
        .sign(hmac512);
  }
}
