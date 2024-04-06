package edu.ntnu.idatt2105.trivium.controller;

import edu.ntnu.idatt2105.trivium.dto.quiz.CreateQuizDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.QuizDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.answer.AnswerDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.result.QuizResultDTO;
import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import edu.ntnu.idatt2105.trivium.model.quiz.answer.Answer;
import edu.ntnu.idatt2105.trivium.model.quiz.featured.FeaturedQuiz;
import edu.ntnu.idatt2105.trivium.model.quiz.result.QuizResult;
import edu.ntnu.idatt2105.trivium.search.Specifications;
import edu.ntnu.idatt2105.trivium.security.AuthIdentity;
import edu.ntnu.idatt2105.trivium.service.QuizService;
import edu.ntnu.idatt2105.trivium.utils.MapperUtils;
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

  @PostMapping
  public ResponseEntity<QuizDTO> create(@AuthenticationPrincipal AuthIdentity identity,
                                  @Validated @RequestBody CreateQuizDTO createQuizDTO) {
    Quiz quiz = modelMapper.map(createQuizDTO, Quiz.class);
    quizService.createQuiz(identity.getId(), createQuizDTO.getCoAuthors(), quiz);
    QuizDTO quizDTO = modelMapper.map(quiz, QuizDTO.class);
    return ResponseEntity.status(HttpStatus.CREATED).body(quizDTO);
  }

  @PostMapping("/{id}/answers")
  public ResponseEntity<QuizResultDTO> answer(
      @AuthenticationPrincipal AuthIdentity identity,
      @PathVariable long id,
      @Validated @RequestBody List<@Valid AnswerDTO> answersDTO) {
    List<Answer> answers = MapperUtils.mapList(answersDTO,
        answerDTO -> modelMapper.map(answerDTO, Answer.class));
    QuizResult result = quizService.answer(identity.getId(), id, answers);
    QuizResultDTO resultDTO = modelMapper.map(result, QuizResultDTO.class);
    return ResponseEntity.ok(resultDTO);
  }

  @GetMapping("/results/{id}")
  public ResponseEntity<QuizResultDTO> getResult(@PathVariable long id) {
    QuizResult result = quizService.getResult(id);
    QuizResultDTO resultDTO = modelMapper.map(result, QuizResultDTO.class);
    return ResponseEntity.ok(resultDTO);
  }

  @GetMapping("/results/users/{userId}")
  public ResponseEntity<List<QuizResultDTO>> getUserResults(@PathVariable long userId) {
    List<QuizResult> results = quizService.getUserResults(userId);
    List<QuizResultDTO> resultDTO = results.stream().map(result -> modelMapper.map(result, QuizResultDTO.class)).toList();
    return ResponseEntity.ok(resultDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<QuizDTO> getQuiz(@PathVariable long id) {
    Quiz quiz = quizService.getQuiz(id);
    QuizDTO quizDTO = modelMapper.map(quiz, QuizDTO.class);
    return ResponseEntity.ok(quizDTO);
  }

  @GetMapping("/featured")
  public ResponseEntity<List<QuizDTO>> getFeatured() {
    List<FeaturedQuiz> quizzes = quizService.getFeatured();
    List<QuizDTO> quizDTO = MapperUtils.mapList(quizzes, quiz -> modelMapper.map(quiz, QuizDTO.class));
    return ResponseEntity.ok(quizDTO);
  }

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
    List<QuizDTO> quizDTO = quizzes.map(quiz -> modelMapper.map(quiz, QuizDTO.class)).toList();
    return ResponseEntity.ok(quizDTO);
  }

  @GetMapping("/{id}/leaderboard")
  public ResponseEntity<List<QuizResultDTO>> getLeaderboard(@PathVariable long id) {
    List<QuizResult> quizzes = quizService.getLeaderboard(id);
    List<QuizResultDTO> quizDTO = MapperUtils.mapList(quizzes, quiz -> modelMapper.map(quiz, QuizResultDTO.class));
    return ResponseEntity.ok(quizDTO);
  }
}
