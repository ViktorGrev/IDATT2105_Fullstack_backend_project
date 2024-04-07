package edu.ntnu.idatt2105.trivium.repository;

import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>, JpaSpecificationExecutor<Quiz> {

  List<Quiz> findAllByCreatorId(long userId);

  List<Quiz> findAllByCoAuthorsId(long userId);
}
