package edu.ntnu.idatt2105.trivium.model.quiz.question;

import edu.ntnu.idatt2105.trivium.properties.QuizProperties;
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

    @Column(name = "option_text", length = QuizProperties.OPTION_TEXT_LEN_MAX, nullable = false)
    private String optionText;

    @Column(name = "correct", nullable = false)
    private boolean correct;
  }
}
