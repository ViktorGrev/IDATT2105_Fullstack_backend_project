package edu.ntnu.idatt2105.trivium.validation;

import edu.ntnu.idatt2105.trivium.validation.impl.TagValidator;
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

public class TagValidatorTest {

  private TagValidator tagValidator;

  @Mock
  private ConstraintValidatorContext context;

  @Mock
  private ConstraintValidatorContext.ConstraintViolationBuilder builder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    tagValidator = new TagValidator();
    tagValidator.initialize(mock(Tag.class));

    when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
  }

  @Test
  void testValidTag() {
    assertTrue(tagValidator.isValid("valid_tag", context));
  }

  @Test
  void testNullTag() {
    assertFalse(tagValidator.isValid(null, context));
  }

  @Test
  void testTagLengthLessThanMinimum() {
    String shortTag = "a";
    assertFalse(tagValidator.isValid(shortTag, context));
  }

  @Test
  void testTagLengthGreaterThanMaximum() {
    String longTag = "this_tag_is_too_long_to_be_valid";
    assertFalse(tagValidator.isValid(longTag, context));
  }

  @Test
  void testInvalidCharactersInTag() {
    String invalidTag = "invalid$tag";
    assertFalse(tagValidator.isValid(invalidTag, context));
  }
}
