package edu.ntnu.idatt2105.trivium.service;

import edu.ntnu.idatt2105.trivium.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

  void insert(User user);

  Optional<User> findByUsername(String username);
}
