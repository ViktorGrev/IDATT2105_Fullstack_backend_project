package edu.ntnu.idatt2105.trivium.repository;

import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
