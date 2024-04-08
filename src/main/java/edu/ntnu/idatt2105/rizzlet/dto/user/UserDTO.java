package edu.ntnu.idatt2105.rizzlet.dto.user;

import edu.ntnu.idatt2105.rizzlet.model.user.Role;
import lombok.Data;

@Data
public class UserDTO {

  private long id;
  private String username;
  private Role role;

}
