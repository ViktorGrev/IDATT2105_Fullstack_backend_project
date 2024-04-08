package edu.ntnu.idatt2105.rizzlet.controller;

import edu.ntnu.idatt2105.rizzlet.dto.auth.CredentialsRequest;
import edu.ntnu.idatt2105.rizzlet.exception.auth.InvalidCredentialsException;
import edu.ntnu.idatt2105.rizzlet.exception.user.UserAlreadyExistsException;
import edu.ntnu.idatt2105.rizzlet.model.user.Role;
import edu.ntnu.idatt2105.rizzlet.model.user.User;
import edu.ntnu.idatt2105.rizzlet.properties.TokenProperties;
import edu.ntnu.idatt2105.rizzlet.properties.UserProperties;
import edu.ntnu.idatt2105.rizzlet.security.SecurityConfig;
import edu.ntnu.idatt2105.rizzlet.service.UserService;
import edu.ntnu.idatt2105.rizzlet.utils.TokenUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Import({UserProperties.class, TokenProperties.class, TokenUtils.class})
@WebMvcTest({AuthController.class, SecurityConfig.class, UserProperties.class, TokenProperties.class, TokenUtils.class})
public class AuthControllerIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService userService;

  @Mock
  private UserProperties userProperties;

  @Mock
  private TokenProperties tokenProperties;

  @Test
  public void whenPostLoginThenReturnToken() throws Exception {
    String username = "username";
    String password = "Password1";
    CredentialsRequest request = new CredentialsRequest(username, password);
    User user = new User();
    user.setUsername(username);
    user.setRole(Role.USER);
    given(userService.loginUser(username, password)).willReturn(user);

    mvc.perform(MockMvcRequestBuilders
            .post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJson(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").exists());
  }

  @Test
  public void whenPostLoginWithInvalidCredentialsThenReturnUnauthorized() throws Exception {
    String username = "username";
    String password = "Password2";
    CredentialsRequest request = new CredentialsRequest(username, password);
    given(userService.loginUser(username, password)).willThrow(new InvalidCredentialsException());

    mvc.perform(MockMvcRequestBuilders
            .post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJson(request)))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void whenPostSignupThenReturnToken() throws Exception {
    String username = "username";
    String password = "Password1";
    CredentialsRequest request = new CredentialsRequest(username, password);
    User user = new User();
    user.setUsername(username);
    user.setRole(Role.USER);
    given(userService.registerUser(username, password)).willReturn(user);

    mvc.perform(MockMvcRequestBuilders
            .post("/api/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJson(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.token").exists());
  }

  @Test
  public void whenPostSignupWithExistingUserThenReturnConflict() throws Exception {
    String username = "existing";
    String password = "Password1";
    CredentialsRequest request = new CredentialsRequest(username, password);
    given(userService.registerUser(username, password)).willThrow(new UserAlreadyExistsException());

    mvc.perform(MockMvcRequestBuilders
            .post("/api/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJson(request)))
        .andExpect(status().isConflict());
  }
}
