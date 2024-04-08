package edu.ntnu.idatt2105.rizzlet.model.user;

import edu.ntnu.idatt2105.rizzlet.dto.user.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class UserTest {

  @Spy
  private ModelMapper modelMapper = new ModelMapper();

  @Test
  public void testUserToUserDTO() {
    User user = new User("username", "Password1");
    user.setId(2);

    UserDTO userDTO = modelMapper.map(user, UserDTO.class);

    assertNotNull(userDTO);
    assertEquals(user.getId(), userDTO.getId());
    assertEquals(user.getUsername(), userDTO.getUsername());
  }

}
