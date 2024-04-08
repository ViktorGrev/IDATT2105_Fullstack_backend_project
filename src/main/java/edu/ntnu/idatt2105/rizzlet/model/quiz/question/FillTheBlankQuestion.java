package edu.ntnu.idatt2105.rizzlet.model.quiz.question;

import edu.ntnu.idatt2105.rizzlet.properties.QuizProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a {@link FillTheBlankQuestion} entity.
 */
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fill_the_blank_question")
public class FillTheBlankQuestion extends Question {

  @Column(name = "solution", length = QuizProperties.SOLUTION_TEXT_LEN_MAX, nullable = false)
  private String solution;
}
