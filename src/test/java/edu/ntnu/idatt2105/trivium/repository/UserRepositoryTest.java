package edu.ntnu.idatt2105.trivium.repository;

import edu.ntnu.idatt2105.trivium.model.user.User;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void findByUsername_ReturnsUser_WhenUserExists() {
    // Arrange
    User user = new User("test_user", "password");
    entityManager.persist(user);
    entityManager.flush();

    // Act
    Optional<User> foundUser = userRepository.findByUsername("test_user");

    // Assert
    assertThat(foundUser).isPresent();
    assertThat(foundUser.get().getUsername()).isEqualTo("test_user");
  }

  @Test
  public void findByUsername_ReturnsEmptyOptional_WhenUserDoesNotExist() {
    // Act
    Optional<User> foundUser = userRepository.findByUsername("non_existent_user");

    // Assert
    assertThat(foundUser).isEmpty();
  }

  @Test
  public void findByUsernameIn_ReturnsUsers_WhenUsersExist() {
    // Arrange
    User user1 = new User("user1", "password1");
    User user2 = new User("user2", "password2");
    entityManager.persist(user1);
    entityManager.persist(user2);
    entityManager.flush();

    // Act
    List<User> foundUsers = userRepository.findByUsernameIn(Arrays.asList("user1", "user2"));

    // Assert
    assertThat(foundUsers).hasSize(2);
    assertThat(foundUsers).extracting(User::getUsername).containsExactlyInAnyOrder("user1", "user2");
  }

  @Test
  public void findByUsernameIn_ReturnsEmptyList_WhenNoUsersExist() {
    // Act
    List<User> foundUsers = userRepository.findByUsernameIn(Arrays.asList("non_existent_user1", "non_existent_user2"));

    // Assert
    assertThat(foundUsers).isEmpty();
  }
}
