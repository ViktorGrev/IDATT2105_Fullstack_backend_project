package edu.ntnu.idatt2105.trivium.service;

import edu.ntnu.idatt2105.trivium.model.user.User;
import org.springframework.stereotype.Service;

/**
 * Service interface defining operations related to user management.
 */
@Service
public interface UserService {

  /**
   * Registers a new user with the provided username and password.
   *
   * @param username The username of the user to register.
   * @param password The password of the user to register.
   * @return The registered user.
   */
  User registerUser(String username, String password);

  /**
   * Logs in a user with the provided username and password.
   *
   * @param username The username of the user to login.
   * @param password The password of the user to login.
   * @return The logged-in user.
   */
  User loginUser(String username, String password);

  /**
   * Finds a user by their username.
   *
   * @param username The username of the user to find.
   * @return A user.
   */
  User findByUsername(String username);
}
