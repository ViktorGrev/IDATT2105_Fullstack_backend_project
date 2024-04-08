package edu.ntnu.idatt2105.rizzlet.validation;

import edu.ntnu.idatt2105.rizzlet.dto.quiz.question.FillTheBlankQuestionDTO;
import edu.ntnu.idatt2105.rizzlet.dto.quiz.question.QuestionDTO;
import edu.ntnu.idatt2105.rizzlet.dto.quiz.question.TrueFalseQuestionDTO;
import edu.ntnu.idatt2105.rizzlet.properties.QuizProperties;
import edu.ntnu.idatt2105.rizzlet.validation.impl.QuestionListValidator;
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

public class QuestionListValidatorTest {

  private QuestionListValidator questionListValidator;

  @Mock
  private ConstraintValidatorContext context;

  @Mock
  private ConstraintValidatorContext.ConstraintViolationBuilder builder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    questionListValidator = new QuestionListValidator();
    questionListValidator.initialize(mock(QuestionList.class));

    when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(builder);
  }

  @Test
  void testValidQuestionList() {
    List<QuestionDTO> validQuestionList = new ArrayList<>();
    validQuestionList.add(new TrueFalseQuestionDTO());
    validQuestionList.add(new FillTheBlankQuestionDTO());
    assertTrue(questionListValidator.isValid(validQuestionList, context));
  }

  @Test
  void testQuestionListExceedingMaxLength() {
    List<QuestionDTO> questionListExceedingMaxLength = new ArrayList<>();
    for (int i = 0; i <= QuizProperties.QUESTION_LIST_LEN_MAX + 1; i++) {
      questionListExceedingMaxLength.add(new TrueFalseQuestionDTO());
    }
    assertFalse(questionListValidator.isValid(questionListExceedingMaxLength, context));
  }

  @Test
  void testEmptyQuestionList() {
    List<QuestionDTO> emptyQuestionList = new ArrayList<>();
    assertFalse(questionListValidator.isValid(emptyQuestionList, context));
  }
}
