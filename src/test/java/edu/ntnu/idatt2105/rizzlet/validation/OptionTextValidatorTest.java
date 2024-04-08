package edu.ntnu.idatt2105.rizzlet.validation;

import edu.ntnu.idatt2105.rizzlet.validation.impl.OptionTextValidator;
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

public class OptionTextValidatorTest {

  private OptionTextValidator optionTextValidator;

  @Mock
  private ConstraintValidatorContext context;

  @Mock
  private ConstraintValidatorContext.ConstraintViolationBuilder builder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    optionTextValidator = new OptionTextValidator();
    optionTextValidator.initialize(mock(OptionText.class));

    when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
  }

  @Test
  void testValidOptionText() {
    assertTrue(optionTextValidator.isValid("Valid option", context));
  }

  @Test
  void testNullOptionText() {
    assertFalse(optionTextValidator.isValid(null, context));
  }

  @Test
  void testOptionTextAtMinimumLength() {
    String minOptionText = "T";
    assertFalse(optionTextValidator.isValid(minOptionText, context));
  }

  @Test
  void testOptionTextAtMaximumLength() {
    String maxOptionText = "Option text at max length";
    assertFalse(optionTextValidator.isValid(maxOptionText, context));
  }
}
