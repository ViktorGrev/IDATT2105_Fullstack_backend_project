package edu.ntnu.idatt2105.trivium.repository;

import edu.ntnu.idatt2105.trivium.model.quiz.result.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

}
