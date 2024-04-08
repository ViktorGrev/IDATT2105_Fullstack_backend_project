package edu.ntnu.idatt2105.rizzlet.service;

import edu.ntnu.idatt2105.rizzlet.exception.quiz.QuizNotFoundException;
import edu.ntnu.idatt2105.rizzlet.exception.quiz.answer.InvalidAnswerFormatException;
import edu.ntnu.idatt2105.rizzlet.model.quiz.Quiz;
import edu.ntnu.idatt2105.rizzlet.model.quiz.answer.Answer;
import edu.ntnu.idatt2105.rizzlet.model.quiz.question.FillTheBlankQuestion;
import edu.ntnu.idatt2105.rizzlet.model.quiz.question.MultipleChoiceQuestion;
import edu.ntnu.idatt2105.rizzlet.model.quiz.question.Question;
import edu.ntnu.idatt2105.rizzlet.model.quiz.question.TrueFalseQuestion;
import edu.ntnu.idatt2105.rizzlet.model.quiz.result.QuizResult;
import edu.ntnu.idatt2105.rizzlet.model.user.User;
import edu.ntnu.idatt2105.rizzlet.repository.QuizRepository;
import edu.ntnu.idatt2105.rizzlet.repository.QuizResultRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class QuizServiceIntegrationTest {

  @Mock
  private QuizRepository quizRepository;

  @Mock
  private QuizResultRepository resultRepository;

  @Mock
  private UserService userService;

  @InjectMocks
  private QuizServiceImpl quizService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testCreateQuizSuccess() {
    long userId = 1L;
    User mockUser = new User("testuser", "testpassword");
    Quiz quiz = new Quiz();
    when(userService.findById(userId)).thenReturn(mockUser);
    when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

    Quiz createdQuiz = quizService.createQuiz(userId, Arrays.asList(), quiz);

    assertNotNull(createdQuiz);
    assertEquals(mockUser, createdQuiz.getCreator());
    verify(quizRepository, times(1)).save(any(Quiz.class));
  }

  @Test
  public void testAnswerSuccess() {
    long userId = 1L;
    long quizId = 1L;
    List<Answer> answers = new ArrayList<>();
    User mockUser = new User("testuser", "testpassword");
    Quiz quiz = new Quiz();
    when(userService.findById(userId)).thenReturn(mockUser);
    when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
    when(resultRepository.save(any(QuizResult.class))).thenReturn(new QuizResult());

    QuizResult result = quizService.answer(userId, quizId, answers);

    assertNotNull(result);
    verify(resultRepository, times(1)).save(any(QuizResult.class));
  }

  @Test
  public void testGetResultSuccess() {
    long resultId = 1L;
    QuizResult mockResult = new QuizResult();
    when(resultRepository.findById(resultId)).thenReturn(Optional.of(mockResult));

    QuizResult result = quizService.getResult(resultId);

    assertNotNull(result);
    assertEquals(mockResult, result);
  }

  @Test
  public void testCalculateScoreTrueFalseSuccess() {
    Quiz quiz = new Quiz();
    Answer answer = new Answer();
    answer.setQuestion(1L);
    answer.setAnswer("true");
    List<Answer> answers = List.of(answer);
    quiz.setQuestions(Arrays.asList(TrueFalseQuestion.builder().id(1L).text("Is the sky blue?").isTrue(true).build()));

    int score = quizService.calculateScore(quiz, answers);

    assertEquals(1, score);
  }

  @Test
  public void testCalculateScoreMultipleChoiceSuccess() {
    Quiz quiz = new Quiz();
    Answer answer = new Answer();
    answer.setQuestion(1L);
    answer.setAnswer("[1]");
    List<Answer> answers = List.of(answer);
    MultipleChoiceQuestion question =MultipleChoiceQuestion.builder().id(1L).text("What is 1+1?").options(List.of(new MultipleChoiceQuestion.Option(1L, "2", true))).build();
    quiz.setQuestions(Arrays.asList(question));

    int score = quizService.calculateScore(quiz, answers);

    assertEquals(1, score);
  }

  @Test
  public void testCalculateScoreFillTheBlankSuccess() {
    Quiz quiz = new Quiz();
    Answer answer = new Answer();
    answer.setQuestion(1L);
    answer.setAnswer("blue");
    List<Answer> answers = List.of(answer);
    FillTheBlankQuestion question = FillTheBlankQuestion.builder().id(1L).text("The color of the sky is ___").solution("blue").build();
    quiz.setQuestions(Arrays.asList(question));

    int score = quizService.calculateScore(quiz, answers);

    assertEquals(1, score);
  }

  @Test
  public void testCalculateScoreInvalidQuestionTypeExceptionThrown() {
    Quiz quiz = new Quiz();
    Answer answer = new Answer();
    answer.setQuestion(1L);
    answer.setAnswer("apple");
    List<Answer> answers = List.of(answer);
    Question question = new Question() {
      @Override
      public Long getId() {
        return 1L;
      }
    };
    quiz.setQuestions(Arrays.asList(question));

    assertThrows(InvalidAnswerFormatException.class, () -> quizService.calculateScore(quiz, answers));
  }

  @Test
  public void testGetQuizSuccess() {
    long quizId = 1L;
    Quiz mockQuiz = new Quiz();
    when(quizRepository.findById(quizId)).thenReturn(Optional.of(mockQuiz));

    Quiz quiz = quizService.getQuiz(quizId);

    assertNotNull(quiz);
    assertEquals(mockQuiz, quiz);
  }

  @Test
  public void testGetQuizQuizNotFound() {
    long quizId = 1L;
    when(quizRepository.findById(quizId)).thenReturn(Optional.empty());

    assertThrows(QuizNotFoundException.class, () -> quizService.getQuiz(quizId));
  }

  @Test
  public void testGetLeaderboardSuccess() {
    long quizId = 1L;
    QuizResult result1 = new QuizResult();
    result1.setScore(10);
    QuizResult result2 = new QuizResult();
    result2.setScore(20);
    List<QuizResult> mockResults = List.of(result1, result2);
    when(resultRepository.findByQuizIdOrderByScoreDesc(quizId)).thenReturn(mockResults);

    List<QuizResult> leaderboard = quizService.getLeaderboard(quizId);

    assertNotNull(leaderboard);
    assertEquals(2, leaderboard.size());
    assertEquals(10, leaderboard.get(0).getScore());
    assertEquals(20, leaderboard.get(1).getScore());
  }

  @Test
  public void testGetLeaderboardNoResultsReturnsEmptyList() {
    long quizId = 1L;
    when(resultRepository.findByQuizIdOrderByScoreDesc(quizId)).thenReturn(new ArrayList<>());

    List<QuizResult> leaderboard = quizService.getLeaderboard(quizId);

    assertNotNull(leaderboard);
    assertTrue(leaderboard.isEmpty());
  }
}
