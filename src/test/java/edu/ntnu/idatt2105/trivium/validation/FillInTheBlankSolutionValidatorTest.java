package edu.ntnu.idatt2105.trivium.validation;

import edu.ntnu.idatt2105.trivium.validation.impl.FillInTheBlankSolutionValidator;
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

public class FillInTheBlankSolutionValidatorTest {

  private FillInTheBlankSolutionValidator fillInTheBlankSolutionValidator;

  @Mock
  private ConstraintValidatorContext context;

  @Mock
  private ConstraintValidatorContext.ConstraintViolationBuilder builder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    fillInTheBlankSolutionValidator = new FillInTheBlankSolutionValidator();
    fillInTheBlankSolutionValidator.initialize(mock(FillTheBlankSolution.class));

    when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
  }

  @Test
  void testValidSolutionText() {
    assertTrue(fillInTheBlankSolutionValidator.isValid("Valid solution", context));
  }

  @Test
  void testNullSolutionText() {
    assertFalse(fillInTheBlankSolutionValidator.isValid(null, context));
  }

  @Test
  void testSolutionTextLengthLessThanMinimum() {
    String shortSolutionText = "";
    assertFalse(fillInTheBlankSolutionValidator.isValid(shortSolutionText, context));
  }

  @Test
  void testSolutionTextLengthGreaterThanMaximum() {
    String longSolutionText = "This solution text is too long to be valid";
    assertFalse(fillInTheBlankSolutionValidator.isValid(longSolutionText, context));
  }
}
