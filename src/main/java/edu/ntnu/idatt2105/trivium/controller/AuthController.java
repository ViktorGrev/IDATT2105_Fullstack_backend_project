package edu.ntnu.idatt2105.trivium.controller;

import edu.ntnu.idatt2105.trivium.dto.security.AuthResponse;
import edu.ntnu.idatt2105.trivium.dto.security.CredentialsRequest;
import edu.ntnu.idatt2105.trivium.exception.auth.InvalidCredentialsException;
import edu.ntnu.idatt2105.trivium.exception.user.UserAlreadyExistsException;
import edu.ntnu.idatt2105.trivium.exception.user.UserNotFoundException;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.utils.TokenUtils;
import edu.ntnu.idatt2105.trivium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller handling authentication related requests.
 */
@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@EnableAutoConfiguration
public class AuthController {

  @Autowired
  private UserService userService;

  /**
   * Handles user login requests.
   *
   * @param request The login request.
   * @return ResponseEntity containing the authentication response.
   * @throws InvalidCredentialsException if the provided credentials are invalid.
   * @throws UserNotFoundException if the user is not found.
   */
  @PostMapping(value = "/login")
  public ResponseEntity<AuthResponse> login(@Validated @RequestBody CredentialsRequest request) {
    User user = userService.loginUser(request.getUsername(), request.getPassword());
    String token = TokenUtils.generateToken(user);
    return ResponseEntity.ok(new AuthResponse(token));
  }

  /**
   * Handles user signup requests.
   *
   * @param request The signup request.
   * @return ResponseEntity containing the authentication response.
   * @throws UserAlreadyExistsException if the user already exists.
   */
  @PostMapping(value = "/signup")
  public ResponseEntity<AuthResponse> signup(@Validated @RequestBody CredentialsRequest request) {
    User user = userService.registerUser(request.getUsername(), request.getPassword());
    String token = TokenUtils.generateToken(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token));
  }
}
