package edu.ntnu.idatt2105.trivium.service;

import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import edu.ntnu.idatt2105.trivium.model.quiz.answer.Answer;
import edu.ntnu.idatt2105.trivium.model.quiz.leaderboard.LeaderboardEntry;
import edu.ntnu.idatt2105.trivium.model.quiz.result.QuizResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {

  Quiz createQuiz(long userId, Quiz quiz);

  QuizResult answer(long userId, long quizId, List<Answer> answers);

  QuizResult getResult(long resultId);

  Quiz getQuiz(long id);

  Page<Quiz> search(Specification<Quiz> spec, Pageable pageable);

  List<Quiz> getQuizzes();

  List<QuizResult> getLeaderboard(long id);

  List<QuizResult> getUserResults(long userId);
}

