package edu.ntnu.idatt2105.trivium.repository;

import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Quiz} entities.
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>, JpaSpecificationExecutor<Quiz> {

  /**
   * Finds all quizzes created by a user.
   *
   * @param userId The ID of the user.
   * @return A list of quizzes created by the user.
   */
  List<Quiz> findAllByCreatorId(long userId);

  /**
   * Finds all quizzes with a user as a co-author.
   *
   * @param userId The ID of the user.
   * @return A list of quizzes with the user as a co-author.
   */
  List<Quiz> findAllByCoAuthorsId(long userId);
}
