package edu.ntnu.idatt2105.rizzlet.validation;

import edu.ntnu.idatt2105.rizzlet.validation.impl.QuizTitleValidator;
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

public class QuizTitleValidatorTest {

  private QuizTitleValidator quizTitleValidator;

  @Mock
  private ConstraintValidatorContext context;

  @Mock
  private ConstraintValidatorContext.ConstraintViolationBuilder builder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    quizTitleValidator = new QuizTitleValidator();
    quizTitleValidator.initialize(mock(QuizTitle.class));

    when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
  }

  @Test
  void testValidTitle() {
    assertTrue(quizTitleValidator.isValid("Valid Title", context));
  }

  @Test
  void testNullTitle() {
    assertFalse(quizTitleValidator.isValid(null, context));
  }

  @Test
  void testTitleLengthLessThanMinimum() {
    String shortTitle = "A";
    assertFalse(quizTitleValidator.isValid(shortTitle, context));
  }

  @Test
  void testTitleLengthGreaterThanMaximum() {
    String longTitle = "This title is too long to be valid";
    assertFalse(quizTitleValidator.isValid(longTitle, context));
  }
}
