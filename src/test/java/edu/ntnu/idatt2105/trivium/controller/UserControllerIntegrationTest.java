package edu.ntnu.idatt2105.trivium.controller;

import edu.ntnu.idatt2105.trivium.model.user.Role;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.security.SecurityConfig;
import edu.ntnu.idatt2105.trivium.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({UserController.class, SecurityConfig.class})
public class UserControllerIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService userService;

  @MockBean
  private UserController userController;

  @MockBean
  private ModelMapper modelMapper;

  @Mock
  private UserDetails userDetails;

  @Test
  public void testUpdateUsername() throws Exception {
    User user = new User();
    user.setId(1);
    user.setUsername("username");
    user.setRole(Role.USER);
    String newUsername = "newUsername";
    when(userService.updateUsername(user.getId(), newUsername)).thenReturn(user);

    mvc.perform(MockMvcRequestBuilders
            .put("/api/users/username/{username}", newUsername)
            .with(SecurityMockMvcRequestPostProcessors.user(user))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetUserByUsername() throws Exception {
    User user = new User();
    user.setUsername("username");
    user.setRole(Role.USER);
    when(userService.findByUsername(user.getUsername())).thenReturn(user);

    mvc.perform(MockMvcRequestBuilders
            .get("/api/users/{username}", user.getUsername())
            .with(SecurityMockMvcRequestPostProcessors.user(user))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetSelf() throws Exception {
    User user = new User();
    user.setId(1);
    user.setUsername("username");
    user.setRole(Role.USER);
    when(userService.findById(1)).thenReturn(user);

    mvc.perform(MockMvcRequestBuilders
            .get("/api/users/self")
            .with(SecurityMockMvcRequestPostProcessors.user(user))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
