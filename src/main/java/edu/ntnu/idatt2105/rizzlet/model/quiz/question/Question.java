package edu.ntnu.idatt2105.rizzlet.model.quiz.question;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a {@link Question} entity.
 */
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;

  @Column(name = "text", length = 64, nullable = false)
  private String text;

  private Type type;

  public enum Type {
    TRUE_FALSE,
    FILL_IN_THE_BLANK,
    MULTIPLE_CHOICE,
    UNKNOWN
  }
}
