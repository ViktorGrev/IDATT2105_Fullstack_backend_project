package edu.ntnu.idatt2105.trivium.search;

import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import edu.ntnu.idatt2105.trivium.model.user.User;
import org.springframework.data.jpa.domain.Specification;

public final class Specifications {

  public static class UserSpec {

    public static Specification<User> withUsername(String username) {
      return (root, query, builder) -> builder.like(root.get("username"), "%" + username + "%");
    }
  }

  public static class QuizSpec {

    public static Specification<Quiz> withTitle(String title) {
      return (root, query, builder) -> builder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Quiz> withDescription(String description) {
      return (root, query, builder) -> builder.like(root.get("description"), "%" + description + "%");
    }

    public static Specification<Quiz> withCategory(Quiz.Category category) {
      return (root, query, builder) -> builder.equal(root.get("category"), category.ordinal());
    }
  }
}
