package edu.ntnu.idatt2105.trivium.model.quiz.tag;

import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

  @Column(name = "name", length = Config.NAME_MAX_LENGTH, nullable = false)
  private String name;

  @ManyToMany(mappedBy = "tags")
  private List<Quiz> quizzes;

  public static class Config {

    public static final int NAME_MIN_LENGTH = 3;
    public static final int NAME_MAX_LENGTH = 16;
  }
}
