package edu.ntnu.idatt2105.trivium.dto.security;

public class AuthResponse {

  private String token;

  public AuthResponse(String token) {
    this.token = token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
