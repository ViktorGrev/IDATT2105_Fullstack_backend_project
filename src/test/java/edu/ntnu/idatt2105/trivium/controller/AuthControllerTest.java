package edu.ntnu.idatt2105.trivium.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2105.trivium.dto.auth.AuthResponse;
import edu.ntnu.idatt2105.trivium.dto.auth.CredentialsRequest;
import edu.ntnu.idatt2105.trivium.exception.auth.InvalidCredentialsException;
import edu.ntnu.idatt2105.trivium.exception.user.UserAlreadyExistsException;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.properties.TokenProperties;
import edu.ntnu.idatt2105.trivium.service.UserService;
import edu.ntnu.idatt2105.trivium.utils.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({TokenProperties.class, TokenUtils.class })
public class AuthControllerTest {

  @Autowired
  private MockMvc mvc;


  @Mock
  private UserService userService;

  @InjectMocks
  private AuthController authController;

  @Test
  void testLoginSuccess() throws Exception {
    CredentialsRequest request = new CredentialsRequest("username", "password");
    User user = new User();
    user.setUsername("username");

    when(userService.loginUser("username", "password")).thenReturn(user);
    String token = TokenUtils.generateToken(user);

    ResponseEntity<AuthResponse> responseEntity = authController.login(request);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(token, responseEntity.getBody().getToken());

    /*try {
      mvc
          .perform(
              MockMvcRequestBuilders
                  .get("/api/auth/login")
                  .with(SecurityMockMvcRequestPostProcessors.user(user))
                  .accept(MediaType.APPLICATION_JSON)
          )
          .andExpect(status().isForbidden());
    } catch (Exception e) {
      fail();
    }

    mvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.toJson(alex))).andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", is("alex")));
    verify(userService, VerificationModeFactory.times(1))
        .save(Mockito.any());
    reset(userService);*/
  }

  @Test
  void testLoginInvalidCredentials() {
    CredentialsRequest credentialsRequest = new CredentialsRequest("username", "password");

    when(userService.loginUser("username", "password")).thenThrow(new InvalidCredentialsException());

    // Assert that InvalidCredentialsException is thrown
    assertThrows(InvalidCredentialsException.class, () -> authController.login(credentialsRequest));
  }

  @Test
  void testSignupSuccess() throws Exception {
    CredentialsRequest credentialsRequest = new CredentialsRequest("username", "password");
    User user = new User();
    user.setUsername("username");

    when(userService.registerUser("username", "password")).thenReturn(user);
    String token = TokenUtils.generateToken(user);

    ResponseEntity<AuthResponse> responseEntity = authController.signup(credentialsRequest);
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals(token, responseEntity.getBody().getToken());
  }

  @Test
  void testSignupUserAlreadyExists() {
    CredentialsRequest credentialsRequest = new CredentialsRequest("username", "password");

    when(userService.registerUser("username", "password")).thenThrow(new UserAlreadyExistsException());

    assertThrows(UserAlreadyExistsException.class, () -> authController.signup(credentialsRequest));
  }
}
