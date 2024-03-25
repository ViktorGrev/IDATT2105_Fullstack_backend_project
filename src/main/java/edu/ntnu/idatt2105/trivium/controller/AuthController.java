package edu.ntnu.idatt2105.trivium.controller;

import edu.ntnu.idatt2105.trivium.dto.security.AuthResponse;
import edu.ntnu.idatt2105.trivium.dto.security.LoginRequest;
import edu.ntnu.idatt2105.trivium.dto.security.SignUpRequest;
import edu.ntnu.idatt2105.trivium.exception.auth.InvalidCredentialsException;
import edu.ntnu.idatt2105.trivium.exception.user.UserAlreadyExistsException;
import edu.ntnu.idatt2105.trivium.exception.user.UserNotFoundException;
import edu.ntnu.idatt2105.trivium.model.User;
import edu.ntnu.idatt2105.trivium.security.TokenUtils;
import edu.ntnu.idatt2105.trivium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Controller handling authentication-related requests.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@EnableAutoConfiguration
public class AuthController {

  @Autowired
  private UserService userService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * Handles user login requests.
   *
   * @param request The login request containing username and password.
   * @return ResponseEntity containing the authentication response.
   * @throws ResponseStatusException if authentication fails due to invalid credentials or user not found.
   */
  @PostMapping(value = "/login")
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<AuthResponse> login(final @RequestBody LoginRequest request) throws ResponseStatusException {
    Optional<User> optionalUser = userService.findByUsername(request.getUsername());
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      boolean match = passwordEncoder.matches(request.getPassword(), user.getPassword());
      if (match) {
        String token = TokenUtils.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
      } else {
        throw new InvalidCredentialsException();
      }
    } else {
      throw new UserNotFoundException();
    }
  }

  /**
   * Handles user signup requests.
   *
   * @param request The signup request containing username and password.
   * @return ResponseEntity containing the authentication response.
   * @throws ResponseStatusException if signup fails due to user already existing.
   */
  @PostMapping(value = "/signup")
  public ResponseEntity<AuthResponse> signup(final @RequestBody SignUpRequest request) {
    Optional<User> optionalUser = userService.findByUsername(request.getUsername());
    if (optionalUser.isEmpty()) {
      String password = passwordEncoder.encode(request.getPassword());
      User user = new User(request.getUsername(), password);
      userService.insert(user);
      String token = TokenUtils.generateToken(user);
      return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token));
    } else {
      throw new UserAlreadyExistsException();
    }
  }
}
