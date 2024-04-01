package edu.ntnu.idatt2105.trivium.dto.quiz;

import edu.ntnu.idatt2105.trivium.dto.quiz.question.QuestionDTO;
import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import edu.ntnu.idatt2105.trivium.model.quiz.tag.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateQuizDTO {

  @Size(min = Quiz.Config.TITLE_MIN_LENGTH, max = Quiz.Config.TITLE_MAX_LENGTH,
      message = "Title must be between {min} and {max} characters")
  @NotNull(message = "Title is required")
  private String title;

  @Nullable
  @Size(max = Quiz.Config.DESCRIPTION_MAX_LENGTH,
      message = "Description must be at most {max} characters")
  private String description;

  @NotNull(message = "Category is required")
  private Quiz.Category category;

  @Valid
  @Nullable
  @Size(max = Quiz.Config.TAGS_MAX_SIZE,
      message = "There must be at most {max} tags")
  private List<@NotEmpty(message = "Tag cannot be empty")
      @Size(min = Tag.Config.NAME_MIN_LENGTH, max = Tag.Config.NAME_MAX_LENGTH,
      message = "Tag must be between {min} and {max} characters") String> tags;

  @Valid
  @Size(min = Quiz.Config.QUESTIONS_MIN_SIZE, max = Quiz.Config.QUESTIONS_MAX_SIZE,
      message = "There must be between {min} and {max} questions")
  @NotNull(message = "A list of questions is required")
  private List<QuestionDTO> questions;

  @Nullable
  private boolean random;

}
