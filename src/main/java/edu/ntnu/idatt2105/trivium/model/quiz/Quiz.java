package edu.ntnu.idatt2105.trivium.model.quiz;

import edu.ntnu.idatt2105.trivium.model.quiz.question.Question;
import edu.ntnu.idatt2105.trivium.model.quiz.tag.Tag;
import edu.ntnu.idatt2105.trivium.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * Represents a {@link Quiz} entity.
 */
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

  @Column(name = "title", length = 32, nullable = false)
  private String title;

  @Column(name = "description", length = 128, nullable = false)
  private String description;

  @Enumerated
  @Column(name = "category", nullable = false)
  private Category category;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "quiz_tag",
      joinColumns = @JoinColumn(name = "quiz_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private List<Tag> tags;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "quiz_id", nullable = false)
  private List<Question> questions;

  @Column(name = "timestamp", nullable = false)
  private Timestamp timestamp;

  @Column(name = "random", nullable = false)
  private boolean random;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator_id", nullable = false)
  private User creator;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "quiz_coauthors",
      joinColumns = @JoinColumn(name = "quiz_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private List<User> coAuthors;

  /**
   * Enum representing quiz categories.
   */
  public enum Category {
    CHEMISTRY,
    CALCULUS,
    ENGINEERING,
    ALGEBRA,
    PHYSICS,
    BIOLOGY,
    LANGUAGE,
    OTHER
  }
}
