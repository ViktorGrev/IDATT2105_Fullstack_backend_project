package edu.ntnu.idatt2105.trivium.repository;

import edu.ntnu.idatt2105.trivium.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

  void registerUser(String username, String password);

  Optional<User> findByUsername(String username);
}
