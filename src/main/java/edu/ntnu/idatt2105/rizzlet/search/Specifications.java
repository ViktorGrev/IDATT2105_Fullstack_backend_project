package edu.ntnu.idatt2105.rizzlet.search;

import edu.ntnu.idatt2105.rizzlet.model.quiz.Quiz;
import edu.ntnu.idatt2105.rizzlet.model.user.User;
import org.springframework.data.jpa.domain.Specification;

/**
 * Utility class for building specifications used for entity filtering.
 */
public final class Specifications {

  /**
   * Specification builder for filtering {@link User} entities.
   */
  public static class UserSpec {

    /**
     * Creates a specification to filter users by username.
     *
     * @param username The username to filter by.
     * @return A specification to filter users by username.
     */
    public static Specification<User> withUsername(String username) {
      return (root, query, builder) -> builder.like(root.get("username"), "%" + username + "%");
    }
  }

  /**
   * Specification builder for filtering {@link Quiz} entities.
   */
  public static class QuizSpec {

    /**
     * Creates a specification to filter quizzes by title.
     *
     * @param title The title to filter by.
     * @return A specification to filter quizzes by title.
     */
    public static Specification<Quiz> withTitle(String title) {
      return (root, query, builder) -> builder.like(root.get("title"), "%" + title + "%");
    }

    /**
     * Creates a specification to filter quizzes by description.
     *
     * @param description The description to filter by.
     * @return A specification to filter quizzes by description.
     */
    public static Specification<Quiz> withDescription(String description) {
      return (root, query, builder) -> builder.like(root.get("description"), "%" + description + "%");
    }

    /**
     * Creates a specification to filter quizzes by category.
     *
     * @param category The category to filter by.
     * @return A specification to filter quizzes by category.
     */
    public static Specification<Quiz> withCategory(Quiz.Category category) {
      return (root, query, builder) -> builder.equal(root.get("category"), category.ordinal());
    }
  }
}
