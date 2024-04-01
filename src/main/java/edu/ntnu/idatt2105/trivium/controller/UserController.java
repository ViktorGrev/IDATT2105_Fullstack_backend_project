package edu.ntnu.idatt2105.trivium.controller;

import edu.ntnu.idatt2105.trivium.dto.user.UserDTO;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.security.AuthIdentity;
import edu.ntnu.idatt2105.trivium.service.UserService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

  @PutMapping("/username/{username}")
  public ResponseEntity<UserDTO> updateUsername(@AuthenticationPrincipal AuthIdentity identity,
                                          @Pattern(
                                              regexp = User.Config.USERNAME_REGEX,
                                              message = User.Config.USERNAME_REGEX_MSG)
                                  @Size(min = User.Config.MIN_USERNAME_LENGTH,
                                      max = User.Config.MAX_USERNAME_LENGTH,
                                  message = "Username must be between {min} and {max} characters")
                                  @NotNull(message = "Username is required")
                                  @PathVariable String username) {
    User user = userService.updateUsername(identity.getId(), username);
    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
    return ResponseEntity.ok(userDTO);
  }
}
