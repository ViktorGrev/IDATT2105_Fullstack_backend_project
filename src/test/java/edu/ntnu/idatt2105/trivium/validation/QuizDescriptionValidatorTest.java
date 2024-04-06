package edu.ntnu.idatt2105.trivium.validation;

import edu.ntnu.idatt2105.trivium.validation.impl.QuizDescriptionValidator;
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

public class QuizDescriptionValidatorTest {

  private QuizDescriptionValidator quizDescriptionValidator;

  @Mock
  private ConstraintValidatorContext context;

  @Mock
  private ConstraintValidatorContext.ConstraintViolationBuilder builder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    quizDescriptionValidator = new QuizDescriptionValidator();
    quizDescriptionValidator.initialize(mock(QuizDescription.class));

    when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
  }

  @Test
  void testValidDescription() {
    assertTrue(quizDescriptionValidator.isValid("Valid Description", context));
  }

  @Test
  void testDescriptionLengthExceedingMaximum() {
    String longDescription = "This description is too long to be valid. " +
        "This description is too long to be valid. This description is too long to be valid. " +
        "This description is too long to be valid";
    assertFalse(quizDescriptionValidator.isValid(longDescription, context));
  }

  @Test
  void testNullDescription() {
    assertTrue(quizDescriptionValidator.isValid(null, context));
  }
}
