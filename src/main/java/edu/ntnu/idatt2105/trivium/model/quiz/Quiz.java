package edu.ntnu.idatt2105.trivium.model.quiz;

import edu.ntnu.idatt2105.trivium.model.quiz.question.Question;
import edu.ntnu.idatt2105.trivium.model.quiz.tag.Tag;
import edu.ntnu.idatt2105.trivium.model.user.User;
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
@Table(name = "quiz")
public class Quiz {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", length = Config.TITLE_MAX_LENGTH, nullable = false)
  private String title;

  @Column(name = "description", length = Config.DESCRIPTION_MAX_LENGTH, nullable = false)
  private String description;

  @Enumerated
  @Column(name = "category", nullable = false)
  private Category category;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "quiz_tag",
      joinColumns = @JoinColumn(name = "quiz_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id")
  )
  private List<Tag> tags;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "quiz_id", nullable = false)
  private List<Question> questions;

  @Column(name = "random", nullable = false)
  private boolean random;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "creator_id", nullable = false)
  private User creator;

  public enum Category {
    SCIENCE,
    MATH,
    HISTORY,
    OTHER
  }

  public static class Config {

    public static final int TITLE_MIN_LENGTH = 4;
    public static final int TITLE_MAX_LENGTH = 32;
    public static final int DESCRIPTION_MAX_LENGTH = 128;
    public static final int TAGS_MAX_SIZE = 16;
    public static final int QUESTIONS_MIN_SIZE = 1;
    public static final int QUESTIONS_MAX_SIZE = 50;
  }
}
