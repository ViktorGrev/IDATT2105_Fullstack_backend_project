package edu.ntnu.idatt2105.trivium.dto.quiz.question;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import edu.ntnu.idatt2105.trivium.model.quiz.question.Question;
import edu.ntnu.idatt2105.trivium.validation.Enumerator;
import edu.ntnu.idatt2105.trivium.validation.QuestionText;
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

  @QuestionText
  private String text;

  @Enumerator(value = Question.Type.class, nullable = false, message = "Invalid question type")
  private String type;
}
