package edu.ntnu.idatt2105.rizzlet.repository;

import edu.ntnu.idatt2105.rizzlet.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link User} entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

  /**
   * Finds a user by their username.
   *
   * @param username The username of the user to find.
   * @return An optional containing the user if found, otherwise empty.
   */
  Optional<User> findByUsername(String username);

  /**
   * Finds users by their usernames.
   *
   * @param usernames The list of usernames to search for.
   * @return A list of users matching the provided usernames.
   */
  List<User> findByUsernameIn(List<String> usernames);
}
