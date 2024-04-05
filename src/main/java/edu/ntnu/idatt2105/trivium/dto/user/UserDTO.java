package edu.ntnu.idatt2105.trivium.dto.user;

import edu.ntnu.idatt2105.trivium.model.user.Role;
import lombok.Data;

@Data
public class UserDTO {

  private long id;
  private String username;
  private Role role;

}
