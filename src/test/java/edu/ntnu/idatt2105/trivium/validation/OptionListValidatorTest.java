package edu.ntnu.idatt2105.trivium.validation;

import edu.ntnu.idatt2105.trivium.dto.quiz.question.MultipleChoiceQuestionDTO;
import edu.ntnu.idatt2105.trivium.properties.QuizProperties;
import edu.ntnu.idatt2105.trivium.validation.impl.OptionListValidator;
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

public class OptionListValidatorTest {

  private OptionListValidator optionListValidator;

  @Mock
  private ConstraintValidatorContext context;

  @Mock
  private ConstraintValidatorContext.ConstraintViolationBuilder builder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    optionListValidator = new OptionListValidator();
    optionListValidator.initialize(mock(OptionList.class));

    when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
  }

  @Test
  void testValidOptionList() {
    List<MultipleChoiceQuestionDTO.OptionDTO> validOptionList = new ArrayList<>();
    validOptionList.add(new MultipleChoiceQuestionDTO.OptionDTO());
    validOptionList.add(new MultipleChoiceQuestionDTO.OptionDTO());
    assertTrue(optionListValidator.isValid(validOptionList, context));
  }

  @Test
  void testOptionListExceedingMaxLength() {
    List<MultipleChoiceQuestionDTO.OptionDTO> optionListExceedingMaxLength = new ArrayList<>();
    for (int i = 0; i <= QuizProperties.OPTION_LIST_LEN_MAX + 1; i++) {
      optionListExceedingMaxLength.add(new MultipleChoiceQuestionDTO.OptionDTO());
    }
    assertFalse(optionListValidator.isValid(optionListExceedingMaxLength, context));
  }

  @Test
  void testEmptyOptionList() {
    List<MultipleChoiceQuestionDTO.OptionDTO> emptyOptionList = new ArrayList<>();
    assertFalse(optionListValidator.isValid(emptyOptionList, context));
  }
}
