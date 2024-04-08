package edu.ntnu.idatt2105.rizzlet.dto.quiz;

import edu.ntnu.idatt2105.rizzlet.dto.quiz.question.QuestionDTO;
import edu.ntnu.idatt2105.rizzlet.model.quiz.Quiz;
import edu.ntnu.idatt2105.rizzlet.validation.*;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class CreateQuizDTO {

  @QuizTitle
  private String title;

  @QuizDescription
  private String description;

  @Enumerator(value = Quiz.Category.class, nullable = false, message = "Invalid category")
  private String category;

  @TagList
  private List<@Tag String> tags;

  @QuestionList
  private List<@Valid QuestionDTO> questions;

  @CoAuthorList
  private List<@Username String> coAuthors;

  private boolean random;

}
