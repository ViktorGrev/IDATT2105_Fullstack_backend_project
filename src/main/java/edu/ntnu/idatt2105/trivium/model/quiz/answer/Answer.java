package edu.ntnu.idatt2105.trivium.model.quiz.answer;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "answer")
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "question_id", nullable = false)
  private long question;

  @Column(name = "answer", nullable = false)
  private String answer;
}
