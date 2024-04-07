package edu.ntnu.idatt2105.trivium.model.user;

import edu.ntnu.idatt2105.trivium.properties.UserProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Represents a user in the system.
 * This class implements the UserDetails interface, providing the necessary information for Spring Security
 * to authenticate and authorize users and for use in testing.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NonNull
  @Column(name = "username", length = UserProperties.NAME_LEN_MAX, unique = true, nullable = false)
  private String username;

  @NonNull
  @Column(name = "password", nullable = false)
  private String password;

  @NonNull
  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private Role role;

  /**
   * Constructs a User object with the given username and password.
   *
   * @param username The username of the user.
   * @param password The password of the user.
   */
  public User(@NonNull String username, @NonNull String password) {
    this.username = username;
    this.password = password;
    this.role = Role.USER;
  }

  /**
   * Returns the authorities granted to the user.
   *
   * @return A list of GrantedAuthority objects representing the authorities granted to the user.
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  /**
   * Indicates whether the user's account has expired.
   *
   * @return true if the user's account is valid (i.e., non-expired), false otherwise.
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * Indicates whether the user is locked or unlocked.
   *
   * @return true if the user is not locked, false otherwise.
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * Indicates whether the user's credentials (password) has expired.
   *
   * @return true if the user's credentials are valid (i.e., non-expired), false otherwise.
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * Indicates whether the user is enabled or disabled.
   *
   * @return true if the user is enabled, false otherwise.
   */
  @Override
  public boolean isEnabled() {
    return true;
  }
}
