package edu.ntnu.idatt2105.trivium.model.quiz.question;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "multiple_choice_question")
public class MultipleChoiceQuestion extends Question {

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "multiple_choice_id")
  private List<Option> options;

  public static class Config {

    public static final int OPTIONS_MIN_SIZE = 1;
    public static final int OPTIONS_MAX_SIZE = 50;
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Entity
  @Table(name = "multiple_choice_option")
  public static class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "option_text", length = Config.TEXT_MAX_LENGTH, nullable = false)
    private String optionText;

    @Column(name = "correct", nullable = false)
    private boolean correct;

    public static class Config {

      public static final int TEXT_MIN_LENGTH = 4;
      public static final int TEXT_MAX_LENGTH = 16;
    }
  }
}
