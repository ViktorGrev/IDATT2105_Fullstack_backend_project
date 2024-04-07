package edu.ntnu.idatt2105.trivium.repository;

import edu.ntnu.idatt2105.trivium.model.quiz.result.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

  List<QuizResult> findAllByUserIdOrderByTimestampDesc(long userId);

  List<QuizResult> findByQuizIdOrderByScoreDesc(long id);

  List<QuizResult> findByQuizId(long id);

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