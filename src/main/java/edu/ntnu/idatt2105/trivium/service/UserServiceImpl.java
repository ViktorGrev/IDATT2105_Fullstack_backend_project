package edu.ntnu.idatt2105.trivium.service;

import edu.ntnu.idatt2105.trivium.exception.auth.InvalidCredentialsException;
import edu.ntnu.idatt2105.trivium.exception.user.UserAlreadyExistsException;
import edu.ntnu.idatt2105.trivium.exception.user.UserNotFoundException;
import edu.ntnu.idatt2105.trivium.exception.user.UsernameTakenException;
import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the {@link UserService} interface providing user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
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
   * {@inheritDoc}
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
   * {@inheritDoc}
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
   * {@inheritDoc}
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

  @Override
  public Page<User> search(Specification<User> spec, Pageable pageable) {
    return userRepository.findAll(spec, pageable);
  }
}