package edu.ntnu.idatt2105.rizzlet.service;

import edu.ntnu.idatt2105.rizzlet.model.quiz.Quiz;
import edu.ntnu.idatt2105.rizzlet.model.quiz.answer.Answer;
import edu.ntnu.idatt2105.rizzlet.model.quiz.difficulty.QuizDifficulty;
import edu.ntnu.idatt2105.rizzlet.model.quiz.featured.FeaturedQuiz;
import edu.ntnu.idatt2105.rizzlet.model.quiz.library.QuizLibrary;
import edu.ntnu.idatt2105.rizzlet.model.quiz.result.QuizResult;
import edu.ntnu.idatt2105.rizzlet.security.AuthIdentity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface defining operations related to quizzes.
 */
@Service
public interface QuizService {

  /**
   * Creates a new quiz.
   *
   * @param userId    The ID of the user creating the quiz.
   * @param coAuthors The list of co-authors for the quiz.
   * @param quiz      The quiz object to create.
   * @return The created quiz.
   */
  Quiz createQuiz(long userId, List<String> coAuthors, Quiz quiz);

  /**
   * Submits answers to a quiz.
   *
   * @param userId The ID of the user submitting the answers.
   * @param quizId The ID of the quiz.
   * @param answers The list of answers submitted by the user.
   * @return The result of answering the quiz.
   */
  QuizResult answer(long userId, long quizId, List<Answer> answers);

  /**
   * Retrieves the result of a quiz.
   *
   * @param resultId The ID of the quiz result.
   * @return The quiz result.
   */
  QuizResult getResult(long resultId);

  /**
   * Retrieves a quiz by its ID.
   *
   * @param id The ID of the quiz.
   * @return The quiz.
   */
  Quiz getQuiz(long id);

  /**
   * Updates an existing quiz.
   *
   * @param quiz     The updated quiz object.
   * @param identity The identity of the user updating the quiz.
   * @return The updated quiz.
   */
  Quiz updateQuiz(Quiz quiz, AuthIdentity identity);

  /**
   * Deletes a quiz.
   *
   * @param quizId   The ID of the quiz to delete.
   * @param identity The identity of the user deleting the quiz.
   */
  void deleteQuiz(long quizId, AuthIdentity identity);

  /**
   * Searches for quizzes based on the given specification and pageable configuration.
   *
   * @param spec     The specification to filter quizzes.
   * @param pageable The pageable configuration for pagination.
   * @return A page of quizzes matching the search criteria.
   */
  Page<Quiz> search(Specification<Quiz> spec, Pageable pageable);

  /**
   * Retrieves a list of featured quizzes.
   *
   * @return The list of featured quizzes.
   */
  List<FeaturedQuiz> getFeatured();

  /**
   * Retrieves the leaderboard for a quiz.
   *
   * @param id The ID of the quiz.
   * @return The leaderboard for the quiz.
   */
  List<QuizResult> getLeaderboard(long id);

  /**
   * Retrieves the results of a user's quizzes.
   *
   * @param userId The ID of the user.
   * @return The list of quiz results for the user.
   */
  List<QuizResult> getUserResults(long userId);

  /**
   * Retrieves the results of a quiz.
   *
   * @param quizId The ID of the quiz.
   * @return The list of quiz results for the quiz.
   */
  List<QuizResult> getResults(long quizId);

  /**
   * Retrieves the difficulty of a quiz.
   *
   * @param quizId The ID of the quiz.
   * @return The quiz difficulty.
   */
  QuizDifficulty getDifficulty(long quizId);

  /**
   * Retrieves the quiz library for a user.
   *
   * @param userId The ID of the user.
   * @return The quiz library for the user.
   */
  QuizLibrary getLibrary(long userId);
}
