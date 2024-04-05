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
  public void testRegisterUser_Success() {
    // Arrange
    String username = "testuser";
    String password = "testpassword";
    when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
    when(userRepository.save(Mockito.any())).thenReturn(new User(username, "encodedPassword"));

    // Act
    User registeredUser = userService.registerUser(username, password);

    // Assert
    assertNotNull(registeredUser);
    assertEquals(username, registeredUser.getUsername());
    verify(userRepository, times(1)).save(Mockito.any());
  }

  @Test
  public void testRegisterUser_UserAlreadyExists() {
    // Arrange
    String username = "existingUser";
    String password = "testpassword";
    when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
    when(userRepository.save(Mockito.any())).thenThrow(DataIntegrityViolationException.class);

    // Act + Assert
    assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(username, password));
  }

  @Test
  public void testLoginUser_Success() {
    // Arrange
    String username = "existingUser";
    String password = "testpassword";
    User mockUser = new User(username, password);
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
    when(passwordEncoder.matches(password, mockUser.getPassword())).thenReturn(true);

    // Act
    User loggedInUser = userService.loginUser(username, password);

    // Assert
    assertNotNull(loggedInUser);
    assertEquals(username, loggedInUser.getUsername());
  }

  @Test
  public void testLoginUser_InvalidCredentials() {
    // Arrange
    String username = "existingUser";
    String password = "invalidPassword";
    User mockUser = new User(username, "testpassword");
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
    when(passwordEncoder.matches(password, mockUser.getPassword())).thenReturn(false);

    // Act + Assert
    assertThrows(InvalidCredentialsException.class, () -> userService.loginUser(username, password));
  }

  @Test
  public void testFindById_Success() {
    // Arrange
    long userId = 1L;
    User mockUser = new User("testuser", "testpassword");
    when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

    // Act
    User foundUser = userService.findById(userId);

    // Assert
    assertNotNull(foundUser);
    assertEquals(mockUser, foundUser);
  }

  @Test
  public void testFindById_UserNotFound() {
    // Arrange
    long userId = 999L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    // Act + Assert
    assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
  }

  @Test
  public void testFindByUsername_Success() {
    // Arrange
    String username = "testuser";
    User mockUser = new User(username, "testpassword");
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

    // Act
    User foundUser = userService.findByUsername(username);

    // Assert
    assertNotNull(foundUser);
    assertEquals(mockUser, foundUser);
  }

  @Test
  public void testFindByUsername_UserNotFound() {
    // Arrange
    String username = "nonexistentUser";
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    // Act + Assert
    assertThrows(UserNotFoundException.class, () -> userService.findByUsername(username));
  }

  @Test
  public void testUpdateUsername_Success() {
    // Arrange
    long userId = 1L;
    String newUsername = "newUsername";
    User mockUser = new User("oldUsername", "testpassword");
    when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
    when(userRepository.save(Mockito.any())).thenReturn(mockUser);

    // Act
    User updatedUser = userService.updateUsername(userId, newUsername);

    // Assert
    assertNotNull(updatedUser);
    assertEquals(newUsername, updatedUser.getUsername());
  }

  @Test
  public void testUpdateUsername_UserNotFound() {
    // Arrange
    long userId = 999L;
    String newUsername = "newUsername";
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    // Act + Assert
    assertThrows(UserNotFoundException.class, () -> userService.updateUsername(userId, newUsername));
  }

  @Test
  public void testUpdateUsername_UsernameTaken() {
    // Arrange
    long userId = 1L;
    String newUsername = "existingUsername";
    User mockUser = new User("oldUsername", "testpassword");
    when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
    when(userRepository.save(Mockito.any())).thenThrow(DataIntegrityViolationException.class);

    // Act + Assert
    assertThrows(UsernameTakenException.class, () -> userService.updateUsername(userId, newUsername));
  }
}
