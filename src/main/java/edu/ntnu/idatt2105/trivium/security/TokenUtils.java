package edu.ntnu.idatt2105.trivium.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import edu.ntnu.idatt2105.trivium.model.user.User;

import java.time.Duration;
import java.time.Instant;

public final class TokenUtils {

  public static final String SECRET = "topsecretkey";
  private static final Duration TOKEN_VALIDITY = Duration.ofMinutes(5);

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
        .withSubject(user.getUsername())
        .withClaim("user_id", user.getId())
        .withIssuer("trivium")
        .withIssuedAt(now)
        .withExpiresAt(now.plusMillis(TOKEN_VALIDITY.toMillis()))
        .sign(hmac512);
  }
}
