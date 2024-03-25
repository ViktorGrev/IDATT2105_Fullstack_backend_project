package edu.ntnu.idatt2105.trivium.security;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class responsible for defining security configurations for the application.
 */
@Configuration
public class SecurityConfig {

  /**
   * Provides a bean for password encoder.
   *
   * @return A PasswordEncoder instance.
   */
  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configures the security filter chain.
   *
   * @param http The HttpSecurity object to configure.
   * @return A SecurityFilterChain instance.
   * @throws Exception If an error occurs during configuration.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.csrf().disable()
        .cors().and()
        .authorizeHttpRequests(auth -> {
          auth.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
              .requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated();
        })
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        .build();
  }
}
