package edu.ntnu.idatt2105.trivium.repository;

import edu.ntnu.idatt2105.trivium.model.quiz.featured.FeaturedQuiz;
import edu.ntnu.idatt2105.trivium.model.quiz.result.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

  List<QuizResult> findAllByUserIdOrderByTimestampDesc(long userId);

  List<QuizResult> findByQuizIdOrderByScoreDesc(long id);

  @Query("SELECT qr.id, AVG(qr.score) AS avg_score, COUNT(qr.id) AS total_results, AVG(qr.score) * COUNT(qr.id) AS popularity_score FROM QuizResult qr GROUP BY qr.id ORDER BY popularity_score DESC LIMIT 10")
  List<FeaturedQuiz> findFeatured();

  /*@Query(value = "with cte_rank as\n" +
      "(\n" +
      "  select cp.UserId,\n" +
      "         cp.CampaignId,\n" +
      "         cp.CountryId,\n" +
      "         cp.Points,\n" +
      "         rank() over(order by cp.Points desc) as RowRank\n" +
      "  from CampaignPoints cp\n" +
      ")\n" +
      "select cr2.*\n" +
      "from cte_rank cr\n" +
      "join cte_rank cr2\n" +
      "  on  cr2.RowRank >= cr.RowRank - @before\n" +
      "  and cr2.RowRank <= cr.RowRank + @after\n" +
      "where cr.UserId = @userId\n" +
      "order by cr2.RowRank;")
  List<QuizResult> lb2();*/
}

/*
with cte_rank as
(
  select cp.id,
         cp.score,
         cp.timestamp,
         cp.user_id,
         rank() over(order by cp.score desc) as RowRank
  from quiz_result cp
)
select cr2.*
from cte_rank cr
join cte_rank cr2
  on  cr2.RowRank >= cr.RowRank - 2
  and cr2.RowRank <= cr.RowRank + 2
where cr.user_id = 7
order by cr2.RowRank;
 */