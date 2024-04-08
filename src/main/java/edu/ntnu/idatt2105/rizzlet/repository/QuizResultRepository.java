package edu.ntnu.idatt2105.rizzlet.repository;

import edu.ntnu.idatt2105.rizzlet.model.quiz.result.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for {@link QuizResult} entities.
 */
@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

  /**
   * Retrieves all quiz results for a user, ordered by timestamp in descending order.
   *
   * @param userId The ID of the user.
   * @return A list of quiz results for the user, ordered by timestamp in descending order.
   */
  List<QuizResult> findAllByUserIdOrderByTimestampDesc(long userId);

  /**
   * Retrieves all quiz results for a quiz, ordered by score in descending order.
   *
   * @param id The ID of the quiz.
   * @return A list of quiz results for the quiz, ordered by score in descending order.
   */
  List<QuizResult> findByQuizIdOrderByScoreDesc(long id);

  /**
   * Retrieves all quiz results for a quiz.
   *
   * @param id The ID of the quiz.
   * @return A list of quiz results for the quiz.
   */
  List<QuizResult> findByQuizId(long id);

  /**
   * Finds featured quizzes based on their popularity score.
   *
   * @param afterDate The timestamp after which to consider quiz results for popularity calculation.
   * @return A list of arrays containing quiz ID, average score, total results, and popularity score.
   */
  @Query("SELECT qr.quiz.id, " +
      "       AVG(qr.score) AS avg_score, " +
      "       COUNT(qr.id) AS total_results, " +
      "       AVG(qr.score) * COUNT(qr.id) AS popularity_score " +
      "FROM QuizResult qr " +
      "WHERE qr.timestamp >= :timestamp " +
      "GROUP BY qr.quiz " +
      "ORDER BY popularity_score DESC " +
      "LIMIT 10")
  List<Object[]> findFeatured(@Param("timestamp") LocalDateTime afterDate);

}