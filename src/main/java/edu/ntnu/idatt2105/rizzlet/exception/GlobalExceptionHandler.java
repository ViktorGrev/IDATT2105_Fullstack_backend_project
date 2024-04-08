package edu.ntnu.idatt2105.rizzlet.exception;

import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.fasterxml.jackson.databind.type.SimpleType;
import edu.ntnu.idatt2105.rizzlet.dto.quiz.question.QuestionDTO;
import edu.ntnu.idatt2105.rizzlet.exception.auth.InvalidCredentialsException;
import edu.ntnu.idatt2105.rizzlet.exception.quiz.QuizNotFoundException;
import edu.ntnu.idatt2105.rizzlet.exception.quiz.answer.InvalidAnswerFormatException;
import edu.ntnu.idatt2105.rizzlet.exception.user.PermissionDeniedException;
import edu.ntnu.idatt2105.rizzlet.exception.user.UserAlreadyExistsException;
import edu.ntnu.idatt2105.rizzlet.exception.user.UserNotFoundException;
import edu.ntnu.idatt2105.rizzlet.exception.user.UsernameTakenException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler that handles various exceptions thrown by the application.
 * Customizes the response for different types of exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handles validation errors for method arguments.
   *
   * @param e The MethodArgumentNotValidException containing validation errors.
   * @param headers The headers for the response.
   * @param status The HTTP status code.
   * @param request The current web request.
   * @return A ResponseEntity containing the error response.
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    String error = e.getMessage();
    FieldError fieldError = e.getFieldError();
    if (fieldError != null) {
      error = fieldError.getDefaultMessage();
    }
    ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), error);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  /**
   * Handles exceptions by returning an {@link HttpStatus#BAD_REQUEST} response.
   *
   * @param e The exception.
   * @return A ResponseEntity containing the error response.
   */
  @ExceptionHandler({InvalidAnswerFormatException.class, ConstraintViolationException.class})
  public ResponseEntity<ExceptionResponse> handleBadRequest(Exception e) {
    String error = e.getMessage();
    if (e instanceof ConstraintViolationException constraintViolationException) {
      for (ConstraintViolation<?> violation : constraintViolationException.getConstraintViolations()) {
        error = violation.getMessage();
        break;
      }
    }
    return ExceptionResponse.toResponseEntity(HttpStatus.BAD_REQUEST, error);
  }

  /**
   * Handles exceptions by returning an {@link HttpStatus#UNAUTHORIZED} response.
   *
   * @param e The exception.
   * @return A ResponseEntity containing the error response.
   */
  @ExceptionHandler({InvalidCredentialsException.class, AccessDeniedException.class,
      AuthenticationException.class, CredentialsExpiredException.class, PermissionDeniedException.class})
  public ResponseEntity<ExceptionResponse> handleUnauthorized(Exception e) {
    return ExceptionResponse.toResponseEntity(HttpStatus.UNAUTHORIZED, e.getMessage());
  }

  /**
   * Handles exceptions by returning an {@link HttpStatus#CONFLICT} response.
   *
   * @param e The exception.
   * @return A ResponseEntity containing the error response.
   */
  @ExceptionHandler({UserAlreadyExistsException.class, UsernameTakenException.class})
  public ResponseEntity<ExceptionResponse> handleConflict(Exception e) {
    return ExceptionResponse.toResponseEntity(HttpStatus.CONFLICT, e.getMessage());
  }

  /**
   * Handles exceptions by returning an {@link HttpStatus#NOT_FOUND} response.
   *
   * @param e The exception.
   * @return A ResponseEntity containing the error response.
   */
  @ExceptionHandler({UserNotFoundException.class, QuizNotFoundException.class})
  public ResponseEntity<ExceptionResponse> handleNotFound(Exception e) {
    return ExceptionResponse.toResponseEntity(HttpStatus.NOT_FOUND, e.getMessage());
  }

  /**
   * Handles remaining exceptions by returning an {@link HttpStatus#INTERNAL_SERVER_ERROR} response.
   *
   * @param e The exception.
   * @return A ResponseEntity containing the error response.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> handleRemainingExceptions(Exception e) {
    return ExceptionResponse.toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
  }

  /**
   * Customizes the default exception handler.
   *
   * @param e The exception.
   * @param body The body of the response.
   * @param headers The headers for the response.
   * @param statusCode The HTTP status code.
   * @param request The current web request.
   * @return A ResponseEntity containing the error response.
   */
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
    if (e instanceof HttpMessageNotReadableException) {
      if (e.getCause() instanceof InvalidTypeIdException typeIdException) {
        if (typeIdException.getBaseType().equals(SimpleType.constructUnsafe(QuestionDTO.class))) {
          ExceptionResponse response = new ExceptionResponse(statusCode.value(), "Question type is required");
          return super.handleExceptionInternal(e, response, headers, statusCode, request);
        }
      }
    }
    ExceptionResponse response = new ExceptionResponse(statusCode.value(), e.getMessage());
    return super.handleExceptionInternal(e, response, headers, statusCode, request);
  }
}
