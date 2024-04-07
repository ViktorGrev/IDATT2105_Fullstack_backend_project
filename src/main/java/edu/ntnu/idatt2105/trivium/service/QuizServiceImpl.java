package edu.ntnu.idatt2105.trivium.service;

import edu.ntnu.idatt2105.trivium.exception.quiz.QuizNotFoundException;
import edu.ntnu.idatt2105.trivium.exception.quiz.answer.InvalidAnswerFormatException;
import edu.ntnu.idatt2105.trivium.exception.quiz.result.ResultNotFoundException;
import edu.ntnu.idatt2105.trivium.exception.user.PermissionDeniedException;
import edu.ntnu.idatt2105.trivium.exception.user.UserNotFoundException;
import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import edu.ntnu.idatt2105.trivium.model.quiz.answer.Answer;
import edu.ntnu.idatt2105.trivium.model.quiz.difficulty.DifficultyLevel;
import edu.ntnu.idatt2105.trivium.model.quiz.difficulty.QuizDifficulty;
import edu.ntnu.idatt2105.trivium.model.quiz.featured.FeaturedQuiz;
import edu.ntnu.idatt2105.trivium.model.quiz.library.QuizLibrary;
import edu.ntnu.idatt2105.trivium.model.quiz.question.FillTheBlankQuestion;
import edu.ntnu.idatt2105.trivium.model.quiz.question.MultipleChoiceQuestion;
import edu.ntnu.idatt2105.trivium.model.quiz.question.Question;
import edu.ntnu.idatt2105.trivium.model.quiz.question.TrueFalseQuestion;
import edu.ntnu.idatt2105.trivium.model.quiz.result.QuizResult;
import edu.ntnu.idatt2105.trivium.model.user.Role;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.repository.QuizRepository;
import edu.ntnu.idatt2105.trivium.repository.QuizResultRepository;
import edu.ntnu.idatt2105.trivium.security.AuthIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of the QuizService interface providing operations related to quizzes.
 */
@Service
public class QuizServiceImpl implements QuizService {

  @Autowired
  private QuizRepository quizRepository;

  @Autowired
  private QuizResultRepository resultRepository;

  @Autowired
  private UserService userService;

  /**
   * Creates a new quiz.
   *
   * @param userId    The ID of the user creating the quiz.
   * @param coAuthors The list of co-authors for the quiz.
   * @param quiz      The quiz object to be created.
   * @return The created quiz.
   */
  @Override
  public Quiz createQuiz(long userId, List<String> coAuthors, Quiz quiz) {
    User creator = userService.findById(userId);
    quiz.setCreator(creator);
    quiz.setTimestamp(Timestamp.from(Instant.now()));
    Map<String, User> coAuthorMap = userService.findByUsernames(coAuthors);
    for (String coAuthor : coAuthors) {
      if (coAuthorMap.keySet().stream().noneMatch(username -> username.equalsIgnoreCase(coAuthor))) {
        throw new UserNotFoundException("Co-author " + coAuthor + " not found");
      }
    }
    quiz.setCoAuthors(coAuthorMap.values().stream().toList());
    return quizRepository.save(quiz);
  }

  /**
   * Allows a user to answer a quiz.
   *
   * @param userId  The ID of the user answering the quiz.
   * @param quizId  The ID of the quiz being answered.
   * @param answers The list of answers provided by the user.
   * @return The quiz result.
   */
  @Override
  public QuizResult answer(long userId, long quizId, List<Answer> answers) {
    User user = userService.findById(userId);
    Quiz quiz = getQuiz(quizId);
    int score = calculateScore(quiz, answers);
    QuizResult result = QuizResult.builder().score(score).user(user)
        .quiz(quiz)
        .timestamp(Timestamp.from(Instant.now())).answers(answers).build();
    return resultRepository.save(result);
  }

  /**
   * Retrieves a quiz result by its ID.
   *
   * @param resultId The ID of the quiz result to retrieve.
   * @return The quiz result.
   * @throws ResultNotFoundException If the quiz result is not found.
   */
  @Override
  public QuizResult getResult(long resultId) {
    Optional<QuizResult> optionalResult = resultRepository.findById(resultId);
    if (optionalResult.isPresent()) {
      return optionalResult.get();
    } else {
      throw new ResultNotFoundException();
    }
  }

  /**
   * Calculates the score for a quiz based on the provided answers.
   *
   * @param quiz    The quiz for which the score is calculated.
   * @param answers The list of answers provided by the user.
   * @return The calculated score.
   */
  public int calculateScore(Quiz quiz, List<Answer> answers) {
    int score = 0;
    for (Answer answer : answers) {
      Question question = getQuestion(quiz, answer.getQuestion());
      if (question instanceof TrueFalseQuestion trueFalse) {
        score += handleAnswer(trueFalse, answer);
      } else if (question instanceof MultipleChoiceQuestion multipleChoice) {
        score += handleAnswer(multipleChoice, answer);
      } else if (question instanceof FillTheBlankQuestion fillTheBlank) {
        score += handleAnswer(fillTheBlank, answer);
      } else {
        throw new InvalidAnswerFormatException("Question " + answer.getQuestion() + " not found");
      }
    }
    return score;
  }

  /**
   * Handles the answer for a TrueFalseQuestion.
   *
   * @param question The TrueFalseQuestion object.
   * @param answer   The Answer object containing the user's answer.
   * @return 1 if the answer is correct, 0 otherwise.
   */
  private int handleAnswer(TrueFalseQuestion question, Answer answer) {
    boolean b = Boolean.parseBoolean(answer.getAnswer());
    return b == question.isTrue() ? 1 : 0;
  }

  /**
   * Handles the answer for a MultipleChoiceQuestion.
   *
   * @param question The MultipleChoiceQuestion object.
   * @param answer   The Answer object containing the user's answer.
   * @return 1 if the answer is correct, 0 otherwise.
   * @throws InvalidAnswerFormatException If the answer format is invalid.
   */
  private int handleAnswer(MultipleChoiceQuestion question, Answer answer) {
    try {
      String a = answer.getAnswer();
      if (a.length() < 3) return 0;
      String[] tokens = a.substring(1, a.length() - 1).split(",");
      List<Long> selectedOptions = new ArrayList<>();
      for (String token : tokens) {
        selectedOptions.add(Long.parseLong(token.trim()));
      }
      boolean isCorrect = true;
      for (MultipleChoiceQuestion.Option option : question.getOptions()) {
        boolean hasSelected = selectedOptions.contains(option.getId());
        if (!option.isCorrect() && hasSelected) {
          isCorrect = false;
        } else if (option.isCorrect() && !hasSelected) {
          isCorrect = false;
        }
      }
      return isCorrect ? 1 : 0;
    } catch (NumberFormatException e) {
      throw new InvalidAnswerFormatException(question.getId());
    }
  }

  /**
   * Handles the answer for a FillTheBlankQuestion.
   *
   * @param question The FillTheBlankQuestion object.
   * @param answer   The Answer object containing the user's answer.
   * @return 1 if the answer is correct, 0 otherwise.
   */
  private int handleAnswer(FillTheBlankQuestion question, Answer answer) {
    return answer.getAnswer().equalsIgnoreCase(question.getSolution()) ? 1 : 0;
  }

  /**
   * Retrieves the question from a quiz by its ID.
   *
   * @param quiz       The Quiz object containing the questions.
   * @param questionId The ID of the question to retrieve.
   * @return The Question object corresponding to the ID, or null if not found.
   */
  private Question getQuestion(Quiz quiz, long questionId) {
    for (Question question : quiz.getQuestions()) {
      if (question.getId().equals(questionId)) {
        return question;
      }
    }
    return null;
  }

  /**
   * Retrieves a quiz by its ID.
   *
   * @param id The ID of the quiz to retrieve.
   * @return The retrieved quiz.
   * @throws QuizNotFoundException If the quiz is not found.
   */
  @Override
  public Quiz getQuiz(long id) {
    Optional<Quiz> optionalQuiz = quizRepository.findById(id);
    if (optionalQuiz.isPresent()) {
      return optionalQuiz.get();
    } else {
      throw new QuizNotFoundException();
    }
  }

  /**
   * Updates an existing quiz.
   *
   * @param quiz     The updated quiz object.
   * @param identity The authentication identity of the user performing the operation.
   * @return The updated quiz.
   * @throws PermissionDeniedException If the user does not have permission to update the quiz.
   */
  @Override
  public Quiz updateQuiz(Quiz quiz, AuthIdentity identity) {
    if (!canEdit(identity, quiz)) {
      throw new PermissionDeniedException();
    }
    return quizRepository.save(quiz);
  }

  public boolean canEdit(AuthIdentity identity, Quiz quiz) {
    return identity.getRole().equals(Role.ADMIN.name())
        || quiz.getCoAuthors().stream().anyMatch(coAuthor -> coAuthor.getId() == identity.getId())
        || quiz.getCreator().getId() == identity.getId();
  }

  /**
   * Deletes a quiz.
   *
   * @param quizId   The ID of the quiz to delete.
   * @param identity The authentication identity of the user performing the operation.
   * @throws PermissionDeniedException If the user does not have permission to delete the quiz.
   * @throws QuizNotFoundException     If the quiz is not found.
   */
  @Override
  public void deleteQuiz(long quizId, AuthIdentity identity) {
    Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);
    if (optionalQuiz.isPresent()) {
      Quiz quiz = optionalQuiz.get();
      if (!canDelete(identity, quiz)) {
        throw new PermissionDeniedException();
      }
      quizRepository.delete(quiz);
    } else {
      throw new QuizNotFoundException();
    }
  }

  public boolean canDelete(AuthIdentity identity, Quiz quiz) {
    return identity.getRole().equals(Role.ADMIN.name()) || quiz.getCreator().getId() == identity.getId();
  }

  /**
   * Searches for quizzes based on the specified criteria.
   *
   * @param spec     The specification for filtering quizzes.
   * @param pageable The pagination information.
   * @return A page of matching quizzes.
   */
  @Override
  public Page<Quiz> search(Specification<Quiz> spec, Pageable pageable) {
    return quizRepository.findAll(spec, pageable);
  }

  /**
   * Retrieves a list of featured quizzes.
   *
   * @return A list of featured quizzes.
   */
  @Override
  public List<FeaturedQuiz> getFeatured() {
    LocalDateTime lastDayDateTime = LocalDateTime.now().minusDays(1);
    List<Object[]> a = resultRepository.findFeatured(lastDayDateTime);
    List<FeaturedQuiz> featuredList = new ArrayList<>();
    List<Quiz> quizzes = quizRepository.findAllById(a.stream().map(objects -> (Long) objects[0]).toList());
    for (int i = 0; i < a.size(); i++) {
      featuredList.add(new FeaturedQuiz(quizzes.get(i), (double) a.get(i)[1], (long) a.get(i)[2], (double) a.get(i)[3]));
    }
    return featuredList;
  }

  /**
   * Retrieves the leaderboard for a quiz.
   *
   * @param id The ID of the quiz.
   * @return The leaderboard for the quiz.
   */
  @Override
  public List<QuizResult> getLeaderboard(long id) {
    return resultRepository.findByQuizIdOrderByScoreDesc(id);
  }

  /**
   * Retrieves the quiz results for a user.
   *
   * @param userId The ID of the user.
   * @return The quiz results for the user.
   */
  @Override
  public List<QuizResult> getUserResults(long userId) {
    return resultRepository.findAllByUserIdOrderByTimestampDesc(userId);
  }

  /**
   * Retrieves the quiz results for a quiz.
   *
   * @param quizId The ID of the quiz.
   * @return The quiz results for the quiz.
   */
  @Override
  public List<QuizResult> getResults(long quizId) {
    return resultRepository.findByQuizId(quizId);
  }

  /**
   * Retrieves the difficulty level of a quiz.
   *
   * @param quizId The ID of the quiz.
   * @return The difficulty level of the quiz.
   */
  @Override
  public QuizDifficulty getDifficulty(long quizId) {
    List<Integer> scores = new ArrayList<>();
    Quiz quiz = getQuiz(quizId);
    for (QuizResult result : getResults(quizId)) {
      scores.add(calculateScore(quiz, result.getAnswers()));
    }
    int sum = 0;
    for (int num : scores) {
      sum += num;
    }
    double averageScore = (double) sum / scores.size();
    double percentage = (averageScore / quiz.getQuestions().size()) * 100;
    return new QuizDifficulty(averageScore, DifficultyLevel.fromPercentage(percentage));
  }

  /**
   * Retrieves the library of quizzes for a user.
   *
   * @param userId The ID of the user.
   * @return The library of quizzes for the user.
   */
  @Override
  public QuizLibrary getLibrary(long userId) {
    List<Quiz> created = quizRepository.findAllByCreatorId(userId);
    List<Quiz> coAuthored = quizRepository.findAllByCoAuthorsId(userId);
    return new QuizLibrary(created, coAuthored);
  }
}
