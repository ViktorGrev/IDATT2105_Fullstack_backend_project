package edu.ntnu.idatt2105.trivium.service;

import edu.ntnu.idatt2105.trivium.exception.auth.InvalidCredentialsException;
import edu.ntnu.idatt2105.trivium.exception.user.UserAlreadyExistsException;
import edu.ntnu.idatt2105.trivium.exception.user.UserNotFoundException;
import edu.ntnu.idatt2105.trivium.exception.user.UsernameTakenException;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of the {@link UserService} interface providing user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  /**
   * Registers a new user with the provided username and password.
   *
   * @param username The username of the user to register.
   * @param password The password of the user to register.
   * @return The registered user.
   * @throws UserAlreadyExistsException If a user with the provided username already exists.
   */
  @Override
  public User registerUser(String username, String password) {
    String encodedPassword = passwordEncoder.encode(password);
    User user = new User(username, encodedPassword);
    try {
      return userRepository.save(user);
    } catch (DataIntegrityViolationException e) {
      throw new UserAlreadyExistsException();
    }
  }

  /**
   * Logs in a user with the provided username and password.
   *
   * @param username The username of the user to login.
   * @param password The password of the user to login.
   * @return The logged-in user.
   * @throws InvalidCredentialsException If the provided credentials are invalid.
   */
  @Override
  public User loginUser(String username, String password) {
    User user = findByUsername(username);
    boolean match = passwordEncoder.matches(password, user.getPassword());
    if (match) {
      return user;
    } else {
      throw new InvalidCredentialsException();
    }
  }

  /**
   * Finds a user by their ID.
   *
   * @param id The ID of the user to find.
   * @return The user with the specified ID.
   * @throws UserNotFoundException If no user with the provided ID is found.
   */
  @Override
  public User findById(long id) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    } else {
      throw new UserNotFoundException();
    }
  }

  /**
   * Finds a user by their username.
   *
   * @param username The username of the user to find.
   * @return The user with the specified username.
   * @throws UserNotFoundException If no user with the provided username is found.
   */
  @Override
  public User findByUsername(String username) {
    Optional<User> optionalUser = userRepository.findByUsername(username);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    } else {
      throw new UserNotFoundException();
    }
  }

  /**
   * Finds multiple users by their usernames.
   *
   * @param usernames The list of usernames to search for.
   * @return A map containing the found users, with usernames as keys.
   */
  @Override
  public Map<String, User> findByUsernames(List<String> usernames) {
    List<User> users = userRepository.findByUsernameIn(usernames);
    Map<String, User> userMap = new HashMap<>();
    for (User user : users) {
      userMap.put(user.getUsername(), user);
    }
    return userMap;
  }

  /**
   * Updates the username of a user.
   *
   * @param userId   The ID of the user.
   * @param username The new username to update.
   * @return The updated user.
   * @throws UserNotFoundException   If no user with the provided ID is found.
   * @throws UsernameTakenException  If the new username is already taken by another user.
   */
  @Override
  public User updateUsername(long userId, String username) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      user.setUsername(username);
      try {
        return userRepository.save(user);
      } catch (DataIntegrityViolationException e) {
        throw new UsernameTakenException();
      }
    } else {
      throw new UserNotFoundException();
    }
  }

  /**
   * Searches for users based on the given specifications and pageable information.
   *
   * @param spec     The specifications to filter users.
   * @param pageable The pageable information for pagination.
   * @return A page containing the users that match the specified criteria.
   */
  @Override
  public Page<User> search(Specification<User> spec, Pageable pageable) {
    return userRepository.findAll(spec, pageable);
  }
}
