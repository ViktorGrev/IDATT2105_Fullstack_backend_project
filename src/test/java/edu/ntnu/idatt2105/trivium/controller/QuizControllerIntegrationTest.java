package edu.ntnu.idatt2105.trivium.controller;

import edu.ntnu.idatt2105.trivium.dto.quiz.CreateQuizDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.answer.AnswerDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.question.TrueFalseQuestionDTO;
import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import edu.ntnu.idatt2105.trivium.model.quiz.difficulty.DifficultyLevel;
import edu.ntnu.idatt2105.trivium.model.quiz.difficulty.QuizDifficulty;
import edu.ntnu.idatt2105.trivium.model.quiz.featured.FeaturedQuiz;
import edu.ntnu.idatt2105.trivium.model.quiz.library.QuizLibrary;
import edu.ntnu.idatt2105.trivium.model.quiz.result.QuizResult;
import edu.ntnu.idatt2105.trivium.model.user.Role;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.security.AuthIdentity;
import edu.ntnu.idatt2105.trivium.security.SecurityConfig;
import edu.ntnu.idatt2105.trivium.service.QuizService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({QuizController.class, SecurityConfig.class})
public class QuizControllerIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private QuizService quizService;

  @MockBean
  private QuizController quizController;

  @MockBean
  private ModelMapper modelMapper;

  @Mock
  private UserDetails userDetails;

  @Test
  public void testCreateQuizValidationErrors() throws Exception {
    User user = new User("username", "password");
    user.setId(1);
    CreateQuizDTO createQuizDTO = new CreateQuizDTO();
    createQuizDTO.setTitle("A");
    createQuizDTO.setCategory("CALCULUS");
    createQuizDTO.setQuestions(Arrays.asList(TrueFalseQuestionDTO.builder().text("Text").build()));

    mvc.perform(MockMvcRequestBuilders
            .post("/api/quiz")
            .with(SecurityMockMvcRequestPostProcessors.user(user))
            .content(JsonUtil.toJson(createQuizDTO))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testCreateQuizUnauthorizedAccess() throws Exception {
    CreateQuizDTO createQuizDTO = new CreateQuizDTO();
    createQuizDTO.setTitle("A test title");
    createQuizDTO.setCategory("CALCULUS");
    createQuizDTO.setQuestions(Arrays.asList(TrueFalseQuestionDTO.builder().text("Text").build()));

    mvc.perform(MockMvcRequestBuilders
            .post("/api/quiz")
            .content(JsonUtil.toJson(createQuizDTO))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void testAnswerQuizSuccessful() throws Exception {
    AuthIdentity authIdentity = new AuthIdentity(1, Role.USER.name());
    User user = new User("username", "password");
    user.setId(1);
    long quizId = 1L;
    List<AnswerDTO> answersDTO = Collections.emptyList();

    QuizResult quizResult = new QuizResult();

    when(quizService.answer(authIdentity.getId(), quizId, Collections.emptyList())).thenReturn(quizResult);

    mvc.perform(MockMvcRequestBuilders
            .post("/api/quiz/{id}/answers", quizId)
            .with(SecurityMockMvcRequestPostProcessors.user(user))
            .content(JsonUtil.toJson(answersDTO))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testAnswerQuizUnauthorizedAccess() throws Exception {
    long quizId = 1L;
    List<AnswerDTO> answersDTO = Collections.emptyList();

    mvc.perform(MockMvcRequestBuilders
            .post("/api/quiz/{id}/answers", quizId)
            .content(JsonUtil.toJson(answersDTO))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void testGetResultSuccessful() throws Exception {
    User user = new User("username", "password");
    user.setId(1);
    long quizResultId = 1L;

    QuizResult quizResult = new QuizResult();

    when(quizService.getResult(quizResultId)).thenReturn(quizResult);

    mvc.perform(MockMvcRequestBuilders
            .get("/api/quiz/results/{id}", quizResultId)
            .with(SecurityMockMvcRequestPostProcessors.user(user))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetUserResultsSuccessful() throws Exception {
    User user = new User("username", "password");
    user.setId(1);

    List<QuizResult> quizResults = Collections.emptyList();

    when(quizService.getUserResults(user.getId())).thenReturn(quizResults);

    mvc.perform(MockMvcRequestBuilders
            .get("/api/quiz/results/users/{userId}", user.getId())
            .with(SecurityMockMvcRequestPostProcessors.user(user))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetLibrarySuccessful() throws Exception {
    User user = new User("username", "password");
    user.setId(1);

    QuizLibrary quizLibrary = new QuizLibrary(Collections.emptyList(), Collections.emptyList());

    when(quizService.getLibrary(user.getId())).thenReturn(quizLibrary);

    mvc.perform(MockMvcRequestBuilders
            .get("/api/quiz/library/{userId}", user.getId())
            .with(SecurityMockMvcRequestPostProcessors.user(user))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetQuizSuccessful() throws Exception {
    User user = new User("username", "password");
    long quizId = 1L;

    Quiz quiz = new Quiz();

    when(quizService.getQuiz(quizId)).thenReturn(quiz);

    mvc.perform(MockMvcRequestBuilders
            .get("/api/quiz/{id}", quizId)
            .with(SecurityMockMvcRequestPostProcessors.user(user))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetQuizDifficultySuccessful() throws Exception {
    User user = new User("username", "password");
    long quizId = 1L;

    QuizDifficulty quizDifficulty = new QuizDifficulty(0.5, DifficultyLevel.HARD);

    when(quizService.getDifficulty(quizId)).thenReturn(quizDifficulty);

    mvc.perform(MockMvcRequestBuilders
            .get("/api/quiz/{id}/difficulty", quizId)
            .with(SecurityMockMvcRequestPostProcessors.user(user))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetFeaturedSuccessful() throws Exception {
    User user = new User("username", "password");
    List<FeaturedQuiz> featuredQuizzes = Collections.emptyList();

    when(quizService.getFeatured()).thenReturn(featuredQuizzes);

    mvc.perform(MockMvcRequestBuilders
            .get("/api/quiz/featured")
            .with(SecurityMockMvcRequestPostProcessors.user(user))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testSearchSuccessful() throws Exception {
    User user = new User("username", "password");
    String title = "Test";
    String description = "Description";
    Quiz.Category category = Quiz.Category.CALCULUS;

    Page<Quiz> quizzes = new PageImpl<>(Collections.emptyList());

    when(quizService.search(any(Specification.class), any(Pageable.class))).thenReturn(quizzes);

    mvc.perform(MockMvcRequestBuilders
            .post("/api/quiz/search")
            .param("title", title)
            .param("description", description)
            .param("category", category.name())
            .with(SecurityMockMvcRequestPostProcessors.user(user))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testGetLeaderboardSuccessful() throws Exception {
    User user = new User("username", "password");
    long quizId = 1L;

    List<QuizResult> leaderboard = Collections.emptyList();

    when(quizService.getLeaderboard(quizId)).thenReturn(leaderboard);

    mvc.perform(MockMvcRequestBuilders
            .get("/api/quiz/{id}/leaderboard", quizId)
            .with(SecurityMockMvcRequestPostProcessors.user(user))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
