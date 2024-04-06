package edu.ntnu.idatt2105.trivium.validation;

import edu.ntnu.idatt2105.trivium.validation.impl.UsernameValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsernameValidatorTest {

  private UsernameValidator usernameValidator;

  @Mock
  private ConstraintValidatorContext context;

  @Mock
  private ConstraintValidatorContext.ConstraintViolationBuilder builder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    usernameValidator = new UsernameValidator();
    usernameValidator.initialize(mock(Username.class));

    when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
  }

  @Test
  public void testValidUsername() {
    String validUsername = "johndoe";
    assertTrue(usernameValidator.isValid(validUsername, context));
  }

  @Test
  public void testNullUsername() {
    String nullUsername = null;
    assertFalse(usernameValidator.isValid(nullUsername, context));
  }

  @Test
  public void testEmptyUsername() {
    String emptyUsername = "";
    assertFalse(usernameValidator.isValid(emptyUsername, context));
  }

  @Test
  public void testUsernameLengthBelowMinimum() {
    String shortUsername = "abc";
    assertFalse(usernameValidator.isValid(shortUsername, context));
  }

  @Test
  public void testUsernameLengthAboveMaximum() {
    String longUsername = "abcdefghijklmnopqrstuvwxyz";
    assertFalse(usernameValidator.isValid(longUsername, context));
  }

  @Test
  public void testInvalidUsernamePattern() {
    String invalidUsername = "john doe";
    assertFalse(usernameValidator.isValid(invalidUsername, context));
  }
}
