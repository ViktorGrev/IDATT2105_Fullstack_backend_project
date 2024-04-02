package edu.ntnu.idatt2105.trivium.controller;

import edu.ntnu.idatt2105.trivium.dto.user.UserDTO;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.security.AuthIdentity;
import edu.ntnu.idatt2105.trivium.service.UserService;
import edu.ntnu.idatt2105.trivium.validation.user.Username;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller handling user related requests.
 */
@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/user")
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
}
