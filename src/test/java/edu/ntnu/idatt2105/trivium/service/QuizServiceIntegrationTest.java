package edu.ntnu.idatt2105.trivium.service;

import edu.ntnu.idatt2105.trivium.exception.quiz.QuizNotFoundException;
import edu.ntnu.idatt2105.trivium.exception.quiz.answer.InvalidAnswerFormatException;
import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import edu.ntnu.idatt2105.trivium.model.quiz.answer.Answer;
import edu.ntnu.idatt2105.trivium.model.quiz.leaderboard.LeaderboardEntry;
import edu.ntnu.idatt2105.trivium.model.quiz.question.FillTheBlankQuestion;
import edu.ntnu.idatt2105.trivium.model.quiz.question.MultipleChoiceQuestion;
import edu.ntnu.idatt2105.trivium.model.quiz.question.Question;
import edu.ntnu.idatt2105.trivium.model.quiz.question.TrueFalseQuestion;
import edu.ntnu.idatt2105.trivium.model.quiz.result.QuizResult;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.repository.QuizRepository;
import edu.ntnu.idatt2105.trivium.repository.QuizResultRepository;
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
  public void testCreateQuiz_Success() {
    // Arrange
    long userId = 1L;
    User mockUser = new User("testuser", "testpassword");
    Quiz quiz = new Quiz();
    when(userService.findById(userId)).thenReturn(mockUser);
    when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

    // Act
    Quiz createdQuiz = quizService.createQuiz(userId, quiz);

    // Assert
    assertNotNull(createdQuiz);
    assertEquals(mockUser, createdQuiz.getCreator());
    verify(quizRepository, times(1)).save(any(Quiz.class));
  }

  @Test
  public void testAnswer_Success() {
    // Arrange
    long userId = 1L;
    long quizId = 1L;
    List<Answer> answers = new ArrayList<>();
    User mockUser = new User("testuser", "testpassword");
    Quiz quiz = new Quiz();
    when(userService.findById(userId)).thenReturn(mockUser);
    when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));
    when(resultRepository.save(any(QuizResult.class))).thenReturn(new QuizResult());

    // Act
    QuizResult result = quizService.answer(userId, quizId, answers);

    // Assert
    assertNotNull(result);
    verify(resultRepository, times(1)).save(any(QuizResult.class));
  }

  @Test
  public void testGetResult_Success() {
    // Arrange
    long resultId = 1L;
    QuizResult mockResult = new QuizResult();
    when(resultRepository.findById(resultId)).thenReturn(Optional.of(mockResult));

    // Act
    QuizResult result = quizService.getResult(resultId);

    // Assert
    assertNotNull(result);
    assertEquals(mockResult, result);
  }

  @Test
  public void testCalculateScore_TrueFalse_Success() {
    // Arrange
    Quiz quiz = new Quiz();
    Answer answer = new Answer();
    answer.setQuestion(1L);
    answer.setAnswer("true");
    List<Answer> answers = List.of(answer);
    quiz.setQuestions(Arrays.asList(TrueFalseQuestion.builder().id(1L).text("Is the sky blue?").isTrue(true).build()));

    // Act
    int score = quizService.calculateScore(quiz, answers);

    // Assert
    assertEquals(1, score);
  }

  @Test
  public void testCalculateScore_MultipleChoice_Success() {
    // Arrange
    Quiz quiz = new Quiz();
    Answer answer = new Answer();
    answer.setQuestion(1L);
    answer.setAnswer("1");
    List<Answer> answers = List.of(answer);
    MultipleChoiceQuestion question =MultipleChoiceQuestion.builder().id(1L).text("What is 1+1?").options(List.of(new MultipleChoiceQuestion.Option(1L, "2", true))).build();
    quiz.setQuestions(Arrays.asList(question));

    // Act
    int score = quizService.calculateScore(quiz, answers);

    // Assert
    assertEquals(1, score);
  }

  @Test
  public void testCalculateScore_FillTheBlank_Success() {
    // Arrange
    Quiz quiz = new Quiz();
    Answer answer = new Answer();
    answer.setQuestion(1L);
    answer.setAnswer("blue");
    List<Answer> answers = List.of(answer);
    FillTheBlankQuestion question = FillTheBlankQuestion.builder().id(1L).text("The color of the sky is ___").solution("blue").build();
    quiz.setQuestions(Arrays.asList(question));

    // Act
    int score = quizService.calculateScore(quiz, answers);

    // Assert
    assertEquals(1, score);
  }

  @Test
  public void testCalculateScore_InvalidQuestionType_ExceptionThrown() {
    // Arrange
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

    // Act + Assert
    assertThrows(InvalidAnswerFormatException.class, () -> quizService.calculateScore(quiz, answers));
  }

  @Test
  public void testGetQuiz_Success() {
    // Arrange
    long quizId = 1L;
    Quiz mockQuiz = new Quiz();
    when(quizRepository.findById(quizId)).thenReturn(Optional.of(mockQuiz));

    // Act
    Quiz quiz = quizService.getQuiz(quizId);

    // Assert
    assertNotNull(quiz);
    assertEquals(mockQuiz, quiz);
  }

  @Test
  public void testGetQuiz_QuizNotFound() {
    // Arrange
    long quizId = 1L;
    when(quizRepository.findById(quizId)).thenReturn(Optional.empty());

    // Act + Assert
    assertThrows(QuizNotFoundException.class, () -> quizService.getQuiz(quizId));
  }

  @Test
  public void testGetQuizzes_Success() {
    // Arrange
    List<Quiz> mockQuizzes = List.of(new Quiz(), new Quiz());
    when(quizRepository.findAll()).thenReturn(mockQuizzes);

    // Act
    List<Quiz> quizzes = quizService.getQuizzes();

    // Assert
    assertNotNull(quizzes);
    assertEquals(2, quizzes.size());
    assertEquals(mockQuizzes, quizzes);
  }

  @Test
  public void testGetLeaderboard_Success() {
    // Arrange
    long quizId = 1L;
    QuizResult result1 = new QuizResult();
    result1.setScore(10);
    QuizResult result2 = new QuizResult();
    result2.setScore(20);
    List<QuizResult> mockResults = List.of(result1, result2);
    when(resultRepository.findByQuizIdOrderByScoreDesc(quizId)).thenReturn(mockResults);

    // Act
    List<QuizResult> leaderboard = quizService.getLeaderboard(quizId);

    // Assert
    assertNotNull(leaderboard);
    assertEquals(2, leaderboard.size());
    assertEquals(10, leaderboard.get(0).getScore());
    assertEquals(20, leaderboard.get(1).getScore());
  }

  @Test
  public void testGetLeaderboard_NoResults_ReturnsEmptyList() {
    // Arrange
    long quizId = 1L;
    when(resultRepository.findByQuizIdOrderByScoreDesc(quizId)).thenReturn(new ArrayList<>());

    // Act
    List<QuizResult> leaderboard = quizService.getLeaderboard(quizId);

    // Assert
    assertNotNull(leaderboard);
    assertTrue(leaderboard.isEmpty());
  }
}
