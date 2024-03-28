package edu.ntnu.idatt2105.trivium.service;

import edu.ntnu.idatt2105.trivium.dto.security.CredentialsRequest;
import edu.ntnu.idatt2105.trivium.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service interface defining operations related to user management.
 */
@Service
public interface UserService {

  /**
   * Registers a new user with the provided credentials.
   *
   * @param request The request containing user credentials.
   * @return The registered user.
   */
  User registerUser(CredentialsRequest request);

  /**
   * Logs in a user with the provided credentials.
   *
   * @param request The request containing user credentials.
   * @return The logged-in user.
   */
  User loginUser(CredentialsRequest request);

  /**
   * Finds a user by their username.
   *
   * @param username The username of the user to find.
   * @return An Optional containing the user, or empty if not found.
   */
  Optional<User> findByUsername(String username);
}
