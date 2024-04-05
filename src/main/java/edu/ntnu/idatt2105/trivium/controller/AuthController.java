package edu.ntnu.idatt2105.trivium.controller;

import edu.ntnu.idatt2105.trivium.dto.auth.AuthResponse;
import edu.ntnu.idatt2105.trivium.dto.auth.CredentialsRequest;
import edu.ntnu.idatt2105.trivium.exception.ExceptionResponse;
import edu.ntnu.idatt2105.trivium.exception.auth.InvalidCredentialsException;
import edu.ntnu.idatt2105.trivium.exception.user.UserAlreadyExistsException;
import edu.ntnu.idatt2105.trivium.exception.user.UserNotFoundException;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.service.UserService;
import edu.ntnu.idatt2105.trivium.utils.TokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller handling authentication related requests.
 */
@Validated
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
  @Operation(summary = "User Login", description = "Handles user login requests.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully logged in"),
      @ApiResponse(responseCode = "400", description = "Invalid credentials",
          content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
  })
  @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
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
  @Operation(summary = "User Signup", description = "Handles user signup requests.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully signed up"),
      @ApiResponse(responseCode = "400", description = "User already exists",
          content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
  })
  @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthResponse> signup(@Validated @RequestBody CredentialsRequest request) {
    User user = userService.registerUser(request.getUsername(), request.getPassword());
    String token = TokenUtils.generateToken(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token));
  }
}
