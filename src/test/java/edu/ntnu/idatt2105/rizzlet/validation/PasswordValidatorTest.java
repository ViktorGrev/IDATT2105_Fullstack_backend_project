package edu.ntnu.idatt2105.rizzlet.validation;

import edu.ntnu.idatt2105.rizzlet.properties.UserProperties;
import edu.ntnu.idatt2105.rizzlet.validation.impl.PasswordValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PasswordValidatorTest {

  private PasswordValidator validator;

  @Mock
  private ConstraintValidatorContext context;

  @Mock
  private ConstraintValidatorContext.ConstraintViolationBuilder builder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    validator = new PasswordValidator();
    validator.initialize(null);
    when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
  }

  @Test
  void initialize_RegexPatternInitialized() {
    assertNotNull(validator.pattern);
  }

  @Test
  void testNullPassword() {
    assertFalse(validator.isValid(null, context));
  }

  @Test
  void testPasswordTooShort() {
    assertFalse(validator.isValid("abc", context));
  }

  @Test
  void testPasswordTooLong() {
    String longPassword = "a".repeat(UserProperties.PASS_LEN_MAX + 1);
    assertFalse(validator.isValid(longPassword, context));
  }

  @Test
  void testInvalidPattern() {
    assertFalse(validator.isValid("invalid_password", context));
  }

  @Test
  void testValidPassword() {
    assertTrue(validator.isValid("ValidPassword123", context));
  }
}