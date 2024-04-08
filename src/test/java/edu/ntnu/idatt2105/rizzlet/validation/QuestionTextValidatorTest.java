package edu.ntnu.idatt2105.rizzlet.validation;

import edu.ntnu.idatt2105.rizzlet.validation.impl.QuestionTextValidator;
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

public class QuestionTextValidatorTest {

  private QuestionTextValidator questionTextValidator;

  @Mock
  private ConstraintValidatorContext context;

  @Mock
  private ConstraintValidatorContext.ConstraintViolationBuilder builder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    questionTextValidator = new QuestionTextValidator();
    questionTextValidator.initialize(mock(QuestionText.class));

    when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
  }

  @Test
  void testValidQuestionText() {
    assertTrue(questionTextValidator.isValid("Valid question text", context));
  }

  @Test
  void testNullQuestionText() {
    assertFalse(questionTextValidator.isValid(null, context));
  }

  @Test
  void testQuestionTextLengthLessThanMinimum() {
    String shortQuestionText = "A";
    assertFalse(questionTextValidator.isValid(shortQuestionText, context));
  }

  @Test
  void testQuestionTextLengthGreaterThanMaximum() {
    String longQuestionText = "This question text is too long to be valid. This question text is too long to be valid" +
        "This question text is too long to be valid. This question text is too long to be valid.";
    assertFalse(questionTextValidator.isValid(longQuestionText, context));
  }
}
