package edu.ntnu.idatt2105.trivium.service;

import edu.ntnu.idatt2105.trivium.exception.auth.InvalidCredentialsException;
import edu.ntnu.idatt2105.trivium.exception.user.UserAlreadyExistsException;
import edu.ntnu.idatt2105.trivium.exception.user.UserNotFoundException;
import edu.ntnu.idatt2105.trivium.exception.user.UsernameTakenException;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserServiceIntegrationTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testRegisterUserSuccess() {
    String username = "testuser";
    String password = "testpassword";
    when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
    when(userRepository.save(Mockito.any())).thenReturn(new User(username, "encodedPassword"));

    User registeredUser = userService.registerUser(username, password);

    assertNotNull(registeredUser);
    assertEquals(username, registeredUser.getUsername());
    verify(userRepository, times(1)).save(Mockito.any());
  }

  @Test
  public void testRegisterUserUserAlreadyExists() {
    String username = "existingUser";
    String password = "testpassword";
    when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
    when(userRepository.save(Mockito.any())).thenThrow(DataIntegrityViolationException.class);

    assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(username, password));
  }

  @Test
  public void testLoginUserSuccess() {
    String username = "existingUser";
    String password = "testpassword";
    User mockUser = new User(username, password);
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
    when(passwordEncoder.matches(password, mockUser.getPassword())).thenReturn(true);

    User loggedInUser = userService.loginUser(username, password);

    assertNotNull(loggedInUser);
    assertEquals(username, loggedInUser.getUsername());
  }

  @Test
  public void testLoginUserInvalidCredentials() {
    String username = "existingUser";
    String password = "invalidPassword";
    User mockUser = new User(username, "testpassword");
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
    when(passwordEncoder.matches(password, mockUser.getPassword())).thenReturn(false);

    assertThrows(InvalidCredentialsException.class, () -> userService.loginUser(username, password));
  }

  @Test
  public void testFindByIdSuccess() {
    long userId = 1L;
    User mockUser = new User("testuser", "testpassword");
    when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

    User foundUser = userService.findById(userId);

    assertNotNull(foundUser);
    assertEquals(mockUser, foundUser);
  }

  @Test
  public void testFindByIdUserNotFound() {
    long userId = 999L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
  }

  @Test
  public void testFindByUsernameSuccess() {
    String username = "testuser";
    User mockUser = new User(username, "testpassword");
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

    User foundUser = userService.findByUsername(username);

    assertNotNull(foundUser);
    assertEquals(mockUser, foundUser);
  }

  @Test
  public void testFindByUsernameUserNotFound() {
    String username = "nonexistentUser";
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.findByUsername(username));
  }

  @Test
  public void testUpdateUsernameSuccess() {
    long userId = 1L;
    String newUsername = "newUsername";
    User mockUser = new User("oldUsername", "testpassword");
    when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
    when(userRepository.save(Mockito.any())).thenReturn(mockUser);

    User updatedUser = userService.updateUsername(userId, newUsername);

    assertNotNull(updatedUser);
    assertEquals(newUsername, updatedUser.getUsername());
  }

  @Test
  public void testUpdateUsernameUserNotFound() {
    long userId = 999L;
    String newUsername = "newUsername";
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userService.updateUsername(userId, newUsername));
  }

  @Test
  public void testUpdateUsernameUsernameTaken() {
    long userId = 1L;
    String newUsername = "existingUsername";
    User mockUser = new User("oldUsername", "testpassword");
    when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
    when(userRepository.save(Mockito.any())).thenThrow(DataIntegrityViolationException.class);

    assertThrows(UsernameTakenException.class, () -> userService.updateUsername(userId, newUsername));
  }
}
