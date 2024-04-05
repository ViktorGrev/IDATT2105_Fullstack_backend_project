package edu.ntnu.idatt2105.trivium.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.ntnu.idatt2105.trivium.model.user.Role;
import edu.ntnu.idatt2105.trivium.utils.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filter responsible for JWT (JSON Web Token) authorization.
 * It extracts the JWT from the request header, validates it, and sets the authentication context.
 */
public class JWTAuthorizationFilter extends OncePerRequestFilter {

  private static final Logger LOGGER = LogManager.getLogger(JWTAuthorizationFilter.class);

  /**
   * Filters incoming requests and processes JWT authorization.
   *
   * @param request The HTTP servlet request.
   * @param response The HTTP servlet response.
   * @param filterChain The filter chain for the request.
   * @throws ServletException If an error occurs during servlet processing.
   * @throws IOException If an I/O error occurs during request processing.
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header == null || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = header.substring(7);
    final DecodedJWT decodedJWT = validateToken(token);
    if (decodedJWT == null) {
      filterChain.doFilter(request, response);
      return;
    }
    long userId = Long.parseLong(decodedJWT.getSubject());
    String userRole = decodedJWT.getClaim("user_role").asString();
    String role = userRole.equals(Role.ADMIN.name()) ? "ROLE_ADMIN" : "ROLE_USER";

    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        new AuthIdentity(userId, role), null,
        Collections.singletonList(new SimpleGrantedAuthority(role)));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }

  /**
   * Validates the JWT token.
   *
   * @param token The JWT token to validate.
   * @return The decoded JWT if valid, null otherwise.
   */
  public DecodedJWT validateToken(final String token) {
    try {
      final Algorithm hmac512 = Algorithm.HMAC512(TokenUtils.SECRET);
      final JWTVerifier verifier = JWT.require(hmac512).build();
      return verifier.verify(token);
    } catch (final JWTVerificationException verificationEx) {
      LOGGER.warn("token is invalid: {}", verificationEx.getMessage());
      return null;
    }
  }
}
