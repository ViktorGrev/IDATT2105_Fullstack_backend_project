package edu.ntnu.idatt2105.rizzlet.validation;

import edu.ntnu.idatt2105.rizzlet.properties.QuizProperties;
import edu.ntnu.idatt2105.rizzlet.validation.impl.CoAuthorListValidator;
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

public class CoAuthorListValidatorTest {

  private CoAuthorListValidator coAuthorListValidator;

  @Mock
  private ConstraintValidatorContext context;

  @Mock
  private ConstraintValidatorContext.ConstraintViolationBuilder builder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    coAuthorListValidator = new CoAuthorListValidator();
    coAuthorListValidator.initialize(mock(CoAuthorList.class));

    when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
  }

  @Test
  void testValidCoAuthorList() {
    List<String> validCoAuthorList = new ArrayList<>();
    validCoAuthorList.add("Author1");
    validCoAuthorList.add("Author2");
    assertTrue(coAuthorListValidator.isValid(validCoAuthorList, context));
  }

  @Test
  void testCoAuthorListExceedingMaxLength() {
    List<String> coAuthorListExceedingMaxLength = new ArrayList<>();
    for (int i = 0; i <= QuizProperties.CO_AUTH_LIST_LEN_MAX + 1; i++) {
      coAuthorListExceedingMaxLength.add("Author" + i);
    }
    assertFalse(coAuthorListValidator.isValid(coAuthorListExceedingMaxLength, context));
  }

  @Test
  void testEmptyCoAuthorList() {
    List<String> emptyCoAuthorList = new ArrayList<>();
    assertTrue(coAuthorListValidator.isValid(emptyCoAuthorList, context));
  }
}
