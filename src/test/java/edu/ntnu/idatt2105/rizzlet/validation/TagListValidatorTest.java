package edu.ntnu.idatt2105.rizzlet.validation;

import edu.ntnu.idatt2105.rizzlet.properties.QuizProperties;
import edu.ntnu.idatt2105.rizzlet.validation.impl.TagListValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TagListValidatorTest {

  private TagListValidator tagListValidator;

  @Mock
  private ConstraintValidatorContext context;

  @Mock
  private ConstraintValidatorContext.ConstraintViolationBuilder builder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    tagListValidator = new TagListValidator();
    tagListValidator.initialize(mock(TagList.class));

    when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
  }

  @Test
  void testValidTagList() {
    List<String> validTagList = new ArrayList<>();
    assertTrue(tagListValidator.isValid(validTagList, context));
  }

  @Test
  void testTagListExceedingMaxLength() {
    List<String> tagListExceedingMaxLength = new ArrayList<>();
    for (int i = 0; i <= QuizProperties.TAG_LIST_LEN_MAX + 1; i++) {
      tagListExceedingMaxLength.add("tag" + i);
    }
    assertFalse(tagListValidator.isValid(tagListExceedingMaxLength, context));
  }

  @Test
  void testEmptyTagList() {
    List<String> emptyTagList = new ArrayList<>();
    assertTrue(tagListValidator.isValid(emptyTagList, context));
  }
}
