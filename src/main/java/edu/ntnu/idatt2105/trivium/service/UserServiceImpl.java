package edu.ntnu.idatt2105.trivium.service;

import edu.ntnu.idatt2105.trivium.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserServiceImpl implements UserService {

  private List<User> users = new ArrayList<>();

  @Override
  public void insert(User user) {
    System.out.println("insert " + user.getUsername());
    users.add(user);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    System.out.println("find " + username);
    for (User user : users) {
      if (user.getUsername().equalsIgnoreCase(username)) return Optional.of(user);
    }
    return Optional.empty();
  }
}