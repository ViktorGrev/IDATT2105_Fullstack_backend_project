package edu.ntnu.idatt2105.trivium.controller;

import edu.ntnu.idatt2105.trivium.dto.user.UserDTO;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.search.Specifications;
import edu.ntnu.idatt2105.trivium.security.AuthIdentity;
import edu.ntnu.idatt2105.trivium.service.UserService;
import edu.ntnu.idatt2105.trivium.validation.Username;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
  @GetMapping("/{username}")
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
  @GetMapping("/self")
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
  @PostMapping(value = "/search")
  public ResponseEntity<List<UserDTO>> search(@RequestParam(required = false) String username, Pageable pageable) {
    Specification<User> spec = Specification.where(null);
    if (username != null) {
      spec = spec.or(Specifications.UserSpec.withUsername(username));
    }
    Page<User> users = userService.search(spec, pageable);
    List<UserDTO> userDTOList = users.map(quiz -> modelMapper.map(quiz, UserDTO.class)).toList();
    return ResponseEntity.ok(userDTOList);
  }
}
