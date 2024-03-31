package edu.ntnu.idatt2105.trivium.model.quiz.question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fill_the_blank_question")
public class FillTheBlankQuestion extends Question {

  @Column(name = "solution", length = Config.SOLUTION_MAX_LENGTH, nullable = false)
  private String solution;

  public static class Config {

    public static final int SOLUTION_MIN_LENGTH = 1;
    public static final int SOLUTION_MAX_LENGTH = 16;
  }
}
