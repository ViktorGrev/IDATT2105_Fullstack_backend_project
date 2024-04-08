package edu.ntnu.idatt2105.trivium.controller;

import edu.ntnu.idatt2105.trivium.dto.user.FeedbackDTO;
import edu.ntnu.idatt2105.trivium.dto.user.SendFeedbackDTO;
import edu.ntnu.idatt2105.trivium.dto.user.UserDTO;
import edu.ntnu.idatt2105.trivium.exception.ExceptionResponse;
import edu.ntnu.idatt2105.trivium.exception.user.PermissionDeniedException;
import edu.ntnu.idatt2105.trivium.model.user.Feedback;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.search.Specifications;
import edu.ntnu.idatt2105.trivium.security.AuthIdentity;
import edu.ntnu.idatt2105.trivium.service.UserService;
import edu.ntnu.idatt2105.trivium.validation.Username;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller handling user related requests.
 */
@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/users")
@EnableAutoConfiguration
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private ModelMapper modelMapper;

  /**
   * Updates the username of the currently authenticated user.
   *
   * @param identity The authenticated user's identity.
   * @param username The new username to update.
   * @return ResponseEntity containing the updated UserDTO.
   */
  @Operation(summary = "Update username", description = "Update the username of a user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
      @ApiResponse(responseCode = "409", description = "Username taken by another user",
          content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
  })
  @PutMapping("/username/{username}")
  public ResponseEntity<UserDTO> updateUsername(
      @AuthenticationPrincipal AuthIdentity identity, @PathVariable @Username String username) {
    User user = userService.updateUsername(identity.getId(), username);
    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
    return ResponseEntity.ok(userDTO);
  }

  /**
   * Retrieves a user by username.
   *
   * @param username The username of the user to retrieve.
   * @return ResponseEntity containing the UserDTO.
   */
  @Operation(summary = "Get user", description = "Get a user by their username.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
  })
  @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> getUserByUsername(@PathVariable @Username String username) {
    User user = userService.findByUsername(username);
    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
    return ResponseEntity.ok(userDTO);
  }

  /**
   * Retrieves the currently authenticated user.
   *
   * @param identity The authenticated user's identity.
   * @return ResponseEntity containing the UserDTO of the authenticated user.
   */
  @Operation(summary = "Get the authenticated user", description = "Get the authenticated user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
  })
  @GetMapping(value = "/self", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> getSelf(@AuthenticationPrincipal AuthIdentity identity) {
    User user = userService.findById(identity.getId());
    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
    return ResponseEntity.ok(userDTO);
  }

  /**
   * Searches for users based on the specified username.
   *
   * @param username The username to search for.
   * @param pageable The pageable object for pagination.
   * @return ResponseEntity containing a list of UserDTOs matching the search criteria.
   */
  @Operation(summary = "Search for users", description = "Search for users based on the parameters.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success")
  })
  @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<UserDTO>> search(@RequestParam(required = false) String username, Pageable pageable) {
    Specification<User> spec = Specification.where(null);
    if (username != null) {
      spec = spec.or(Specifications.UserSpec.withUsername(username));
    }
    Page<User> users = userService.search(spec, pageable);
    List<UserDTO> userDTOList = users.map(quiz -> modelMapper.map(quiz, UserDTO.class)).toList();
    return ResponseEntity.ok(userDTOList);
  }

  /**
   * Sends feedback from a user.
   *
   * @param identity The authenticated user's identity.
   */
  @Operation(summary = "Send feedback", description = "Send feedback from a user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success")
  })
  @PostMapping("/feedback")
  public ResponseEntity<Void> sendFeedback(@AuthenticationPrincipal AuthIdentity identity,
                                           @Validated @RequestBody SendFeedbackDTO feedbackDTO) {
    userService.sendFeedback(identity.getId(), feedbackDTO.getEmail(), feedbackDTO.getMessage());
    return ResponseEntity.ok().build();
  }

  /**
   * Get all feedback.
   *
   * @param identity The authenticated user's identity.
   * @return A list containing all feedback.
   */
  @Operation(summary = "Send feedback", description = "Send feedback from a user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success")
  })
  @GetMapping("/feedback")
  public ResponseEntity<List<FeedbackDTO>> getFeedback(@AuthenticationPrincipal AuthIdentity identity) {
    if (!identity.getRole().equalsIgnoreCase("ADMIN")) {
      throw new PermissionDeniedException();
    }
    List<Feedback> feedbacks = userService.getFeedback();
    List<FeedbackDTO> feedbackDTOS = feedbacks.stream().map(quiz -> modelMapper.map(quiz, FeedbackDTO.class)).toList();
    return ResponseEntity.ok(feedbackDTOS);
  }
}
