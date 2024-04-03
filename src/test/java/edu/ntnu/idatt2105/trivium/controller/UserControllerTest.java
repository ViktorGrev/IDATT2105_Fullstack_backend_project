package edu.ntnu.idatt2105.trivium.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2105.trivium.dto.user.UserDTO;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.security.AuthIdentity;
import edu.ntnu.idatt2105.trivium.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

  @Mock
  private UserService userService;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private UserController userController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testUpdateUsernameSuccess() {
    AuthIdentity authIdentity = new AuthIdentity(1L, "username");
    User user = new User();
    user.setId(1L);
    user.setUsername("new_username");
    UserDTO userDTO = new UserDTO();
    userDTO.setId(1L);
    userDTO.setUsername("new_username");

    when(userService.updateUsername(1L, "new_username")).thenReturn(user);
    when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

    ResponseEntity<UserDTO> responseEntity = userController.updateUsername(authIdentity, "new_username");
    assertEquals(userDTO, responseEntity.getBody());
  }

  @Test
  public void testGetUserSuccess() {
    long userId = 1L;
    User user = new User();
    user.setId(userId);
    user.setUsername("username");
    UserDTO userDTO = new UserDTO();
    userDTO.setId(userId);
    userDTO.setUsername("username");

    when(userService.findById(userId)).thenReturn(user);
    when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

    ResponseEntity<UserDTO> responseEntity = userController.getUser(userId);
    assertEquals(userDTO, responseEntity.getBody());
  }
}
