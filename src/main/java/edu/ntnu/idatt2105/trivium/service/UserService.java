package edu.ntnu.idatt2105.trivium.service;

import edu.ntnu.idatt2105.trivium.model.user.Feedback;
import edu.ntnu.idatt2105.trivium.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
   * Finds a user by their ID.
   *
   * @param id The ID of the user to find.
   * @return The user with the specified ID.
   */
  User findById(long id);

  /**
   * Finds a user by their username.
   *
   * @param username The username of the user to find.
   * @return A user.
   */
  User findByUsername(String username);

  /**
   * Finds multiple users by their usernames.
   *
   * @param usernames The list of usernames to search for.
   * @return A map containing the found users, with usernames as keys.
   */
  Map<String, User> findByUsernames(List<String> usernames);

  /**
   * Updates the username of a user.
   *
   * @param userId The ID of the user.
   * @param username The new username to update.
   * @return The updated user.
   */
  User updateUsername(long userId, String username);

  /**
   * Searches for users based on the given specifications and pageable information.
   *
   * @param spec     The specifications to filter users.
   * @param pageable The pageable information for pagination.
   * @return A page containing the users that match the specified criteria.
   */
  Page<User> search(Specification<User> spec, Pageable pageable);

  /**
   * Sends feedback from a user.
   *
   * @param userId The ID of the user.
   * @param email The email.
   * @param message The message.
   */
  void sendFeedback(long userId, String email, String message);

  /**
   * Get all feedback.
   *
   * @return A list containing all feedback.
   */
  List<Feedback> getFeedback();
}
