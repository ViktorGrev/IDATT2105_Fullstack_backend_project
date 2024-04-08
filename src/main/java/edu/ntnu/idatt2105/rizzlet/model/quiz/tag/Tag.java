package edu.ntnu.idatt2105.rizzlet.model.quiz.tag;

import edu.ntnu.idatt2105.rizzlet.model.quiz.Quiz;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a {@link Tag} entity.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tag")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", length = 12, nullable = false)
  private String name;

  @ManyToMany(mappedBy = "tags")
  private List<Quiz> quizzes;
}
