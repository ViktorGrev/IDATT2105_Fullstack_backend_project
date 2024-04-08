package edu.ntnu.idatt2105.rizzlet.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Represents an exception response containing status code and message.
 */
@Data
@AllArgsConstructor
public class ExceptionResponse {

  private int status;
  private String message;

  /**
   * Creates a ResponseEntity containing the exception response.
   *
   * @param status The HTTP status code.
   * @param message The message describing the exception.
   * @return A ResponseEntity containing the exception response.
   */
  protected static ResponseEntity<ExceptionResponse> toResponseEntity(HttpStatus status, String message) {
    ExceptionResponse response = new ExceptionResponse(status.value(), message);
    return ResponseEntity.status(status).body(response);
  }
}
