package edu.ntnu.idatt2105.trivium.controller;

import edu.ntnu.idatt2105.trivium.dto.quiz.CreateQuizDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.QuizDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.answer.AnswerDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.difficulty.QuizDifficultyDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.featured.FeaturedQuizDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.library.QuizLibraryDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.result.QuizResultDTO;
import edu.ntnu.idatt2105.trivium.exception.user.PermissionDeniedException;
import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import edu.ntnu.idatt2105.trivium.model.quiz.answer.Answer;
import edu.ntnu.idatt2105.trivium.model.quiz.difficulty.QuizDifficulty;
import edu.ntnu.idatt2105.trivium.model.quiz.featured.FeaturedQuiz;
import edu.ntnu.idatt2105.trivium.model.quiz.library.QuizLibrary;
import edu.ntnu.idatt2105.trivium.model.quiz.result.QuizResult;
import edu.ntnu.idatt2105.trivium.search.Specifications;
import edu.ntnu.idatt2105.trivium.security.AuthIdentity;
import edu.ntnu.idatt2105.trivium.service.QuizService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller class responsible for handling endpoints related to quizzes.
 */
@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/quiz")
@EnableAutoConfiguration
public class QuizController {

  @Autowired
  private QuizService quizService;

  @Autowired
  private ModelMapper modelMapper;

  /**
   * Endpoint for creating a new quiz.
   *
   * @param identity      The authenticated user's identity.
   * @param createQuizDTO DTO containing information for creating a quiz.
   * @return ResponseEntity containing the created quiz DTO.
   */
  @PostMapping
  public ResponseEntity<QuizDTO> create(@AuthenticationPrincipal AuthIdentity identity,
                                  @Validated @RequestBody CreateQuizDTO createQuizDTO) {
    Quiz quiz = modelMapper.map(createQuizDTO, Quiz.class);
    quizService.createQuiz(identity.getId(), createQuizDTO.getCoAuthors(), quiz);
    QuizDTO quizDTO = modelMapper.map(quiz, QuizDTO.class);
    return ResponseEntity.status(HttpStatus.CREATED).body(quizDTO);
  }

  /**
   * Endpoint for submitting answers to a quiz.
   *
   * @param identity    The authenticated user's identity.
   * @param id          The ID of the quiz.
   * @param answersDTO  List of answer DTOs submitted by the user.
   * @return ResponseEntity containing the quiz result DTO.
   */
  @PostMapping("/{id}/answers")
  public ResponseEntity<QuizResultDTO> answer(
      @AuthenticationPrincipal AuthIdentity identity,
      @PathVariable long id,
      @Validated @RequestBody List<@Valid AnswerDTO> answersDTO) {
    List<Answer> answers = answersDTO.stream().map(answerDTO ->
        modelMapper.map(answerDTO, Answer.class)).toList();
    QuizResult result = quizService.answer(identity.getId(), id, answers);
    QuizResultDTO resultDTO = modelMapper.map(result, QuizResultDTO.class);
    return ResponseEntity.ok(resultDTO);
  }

  /**
   * Endpoint for getting the result of a quiz.
   *
   * @param identity The authenticated user's identity.
   * @param id       The ID of the quiz result.
   * @return ResponseEntity containing the quiz result DTO.
   */
  @GetMapping("/results/{id}")
  public ResponseEntity<QuizResultDTO> getResult(@AuthenticationPrincipal AuthIdentity identity,
                                                 @PathVariable long id) {
    QuizResult result = quizService.getResult(id);
    if (identity.getId() != result.getUser().getId()) {
      throw new PermissionDeniedException();
    }
    QuizResultDTO resultDTO = modelMapper.map(result, QuizResultDTO.class);
    return ResponseEntity.ok(resultDTO);
  }

  /**
   * Endpoint for getting all results of a user.
   *
   * @param userId The ID of the user.
   * @return ResponseEntity containing a list of quiz result DTOs.
   */
  @GetMapping("/results/users/{userId}")
  public ResponseEntity<List<QuizResultDTO>> getUserResults(@PathVariable long userId) {
    List<QuizResult> results = quizService.getUserResults(userId);
    List<QuizResultDTO> resultDTO = results.stream().map(result -> modelMapper.map(result, QuizResultDTO.class)).toList();
    return ResponseEntity.ok(resultDTO);
  }

  /**
   * Endpoint for getting the library of quizzes for a user.
   *
   * @param userId The ID of the user.
   * @return ResponseEntity containing the quiz library DTO.
   */
  @GetMapping("/library/{userId}")
  public ResponseEntity<QuizLibraryDTO> getLibrary(@PathVariable long userId) {
    QuizLibrary library = quizService.getLibrary(userId);
    QuizLibraryDTO resultDTO = modelMapper.map(library, QuizLibraryDTO.class);
    return ResponseEntity.ok(resultDTO);
  }

  /**
   * Endpoint for getting a specific quiz by ID.
   *
   * @param id The ID of the quiz.
   * @return ResponseEntity containing the quiz DTO.
   */
  @GetMapping("/{id}")
  public ResponseEntity<QuizDTO> getQuiz(@PathVariable long id) {
    Quiz quiz = quizService.getQuiz(id);
    QuizDTO quizDTO = modelMapper.map(quiz, QuizDTO.class);
    return ResponseEntity.ok(quizDTO);
  }

  /**
   * Endpoint for updating an existing quiz.
   *
   * @param identity     The authenticated user's identity.
   * @param updateQuizDTO DTO containing information for updating a quiz.
   * @return ResponseEntity containing the updated quiz DTO.
   */
  @PutMapping("/{id}")
  public ResponseEntity<QuizDTO> updateQuiz(@AuthenticationPrincipal AuthIdentity identity,
                                            @Validated @RequestBody QuizDTO updateQuizDTO) {
    Quiz quiz = modelMapper.map(updateQuizDTO, Quiz.class);
    Quiz updatedQuiz = quizService.updateQuiz(quiz, identity);
    QuizDTO quizDTO = modelMapper.map(updatedQuiz, QuizDTO.class);
    return ResponseEntity.ok(quizDTO);
  }

  /**
   * Endpoint for deleting a quiz.
   *
   * @param identity The authenticated user's identity.
   * @param id       The ID of the quiz to delete.
   * @return ResponseEntity with no content.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteQuiz(@AuthenticationPrincipal AuthIdentity identity,
                                         @PathVariable long id) {
    quizService.deleteQuiz(id, identity);
    return ResponseEntity.ok().build();
  }

  /**
   * Endpoint for getting the difficulty of a quiz.
   *
   * @param id The ID of the quiz.
   * @return ResponseEntity containing the quiz difficulty DTO.
   */
  @GetMapping("/{id}/difficulty")
  public ResponseEntity<QuizDifficultyDTO> getQuizDifficulty(@PathVariable long id) {
    QuizDifficulty difficulty = quizService.getDifficulty(id);
    QuizDifficultyDTO quizDTO = modelMapper.map(difficulty, QuizDifficultyDTO.class);
    return ResponseEntity.ok(quizDTO);
  }

  /**
   * Endpoint for getting the featured quizzes.
   *
   * @return ResponseEntity containing a list of featured quiz DTOs.
   */
  @GetMapping("/featured")
  public ResponseEntity<List<FeaturedQuizDTO>> getFeatured() {
    List<FeaturedQuiz> quizzes = quizService.getFeatured();
    List<FeaturedQuizDTO> quizDTO = quizzes.stream().map(quiz ->
        modelMapper.map(quiz, FeaturedQuizDTO.class)).toList();
    return ResponseEntity.ok(quizDTO);
  }

  /**
   * Endpoint for searching quizzes based on title, description, and category.
   *
   * @param title       The title of the quiz (optional).
   * @param description The description of the quiz (optional).
   * @param category    The category of the quiz (optional).
   * @return ResponseEntity containing a list of quiz DTOs that match the search criteria.
   */
  @PostMapping("/search")
  public ResponseEntity<List<QuizDTO>> search(
      @RequestParam(required = false) String title,
      @RequestParam(required = false) String description,
      @RequestParam(required = false) Quiz.Category category) {
    Specification<Quiz> spec = Specification.where(null);
    if (title != null) {
      spec = spec.or(Specifications.QuizSpec.withTitle(title));
    }
    if (description != null) {
      spec = spec.or(Specifications.QuizSpec.withDescription(description));
    }
    if (category != null) {
      spec = spec.or(Specifications.QuizSpec.withCategory(category));
    }
    Page<Quiz> quizzes = quizService.search(spec, Pageable.unpaged());
    List<QuizDTO> quizDTO = quizzes.map(quiz ->
        modelMapper.map(quiz, QuizDTO.class)).toList();
    return ResponseEntity.ok(quizDTO);
  }

  /**
   * Endpoint for getting the leaderboard of a quiz.
   *
   * @param id The ID of the quiz.
   * @return ResponseEntity containing a list of quiz result DTOs representing the leaderboard.
   */
  @GetMapping("/{id}/leaderboard")
  public ResponseEntity<List<QuizResultDTO>> getLeaderboard(@PathVariable long id) {
    List<QuizResult> quizzes = quizService.getLeaderboard(id);
    List<QuizResultDTO> quizDTO = quizzes.stream().map(quiz ->
        modelMapper.map(quiz, QuizResultDTO.class)).toList();
    return ResponseEntity.ok(quizDTO);
  }
}
