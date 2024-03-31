package edu.ntnu.idatt2105.trivium.dto.quiz.question;

import com.fasterxml.jackson.annotation.*;
import edu.ntnu.idatt2105.trivium.model.quiz.question.Question;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true, include = JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes({
    @JsonSubTypes.Type(value = TrueFalseQuestionDTO.class, name = "TRUE_FALSE"),
    @JsonSubTypes.Type(value = FillTheBlankQuestionDTO.class, name = "FILL_IN_THE_BLANK"),
    @JsonSubTypes.Type(value = MultipleChoiceQuestionDTO.class, name = "MULTIPLE_CHOICE")
})
public abstract class QuestionDTO {

  @Nullable
  private long id;

  @Size(min = Question.Config.TEXT_MIN_LENGTH, max = Question.Config.TEXT_MAX_LENGTH,
      message = "Question text must be between {min} and {max} characters")
  @NotNull(message = "Question text is required")
  private String text;

  @NotNull(message = "Question type is required")
  private Question.Type type;
}
