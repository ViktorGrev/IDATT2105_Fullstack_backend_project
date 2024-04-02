package edu.ntnu.idatt2105.trivium.model.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "username", length = 16, unique = true, nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
