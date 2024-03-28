package edu.ntnu.idatt2105.trivium.service;

import edu.ntnu.idatt2105.trivium.dto.security.CredentialsRequest;
import edu.ntnu.idatt2105.trivium.exception.auth.InvalidCredentialsException;
import edu.ntnu.idatt2105.trivium.exception.user.UserNotFoundException;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
   * {@inheritDoc}
   */
  @Override
  public User registerUser(String username, String password) {
    String encodedPassword = passwordEncoder.encode(password);
    User user = new User(username, encodedPassword);
    userRepository.insert(user);
    return user;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public User loginUser(String username, String password) {
    Optional<User> optionalUser = findByUsername(username);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      boolean match = passwordEncoder.matches(password, user.getPassword());
      if (match) {
        return user;
      } else {
        throw new InvalidCredentialsException();
      }
    } else {
      throw new UserNotFoundException();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}