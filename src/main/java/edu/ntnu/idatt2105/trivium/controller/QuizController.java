package edu.ntnu.idatt2105.trivium.controller;

import edu.ntnu.idatt2105.trivium.dto.quiz.CreateQuizDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.QuizDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.answer.AnswerDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.leaderboard.LeaderboardEntryDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.result.QuizResultDTO;
import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import edu.ntnu.idatt2105.trivium.model.quiz.answer.Answer;
import edu.ntnu.idatt2105.trivium.model.quiz.leaderboard.LeaderboardEntry;
import edu.ntnu.idatt2105.trivium.model.quiz.result.QuizResult;
import edu.ntnu.idatt2105.trivium.security.AuthIdentity;
import edu.ntnu.idatt2105.trivium.service.QuizService;
import edu.ntnu.idatt2105.trivium.utils.MapperUtils;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
                                  @RequestBody CreateQuizDTO createQuizDTO) {
    Quiz quiz = modelMapper.map(createQuizDTO, Quiz.class);
    quizService.createQuiz(identity.getId(), quiz);
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

  @GetMapping(value = "/{id}")
  public ResponseEntity<QuizDTO> find(@PathVariable long id) {
    Quiz quiz = quizService.getQuiz(id);
    QuizDTO quizDTO = modelMapper.map(quiz, QuizDTO.class);
    return ResponseEntity.ok(quizDTO);
  }

  @GetMapping(value = "/recent")
  public ResponseEntity<List<QuizDTO>> findRecent() {
    List<Quiz> quizzes = quizService.getQuizzes();
    List<QuizDTO> quizDTO = MapperUtils.mapList(quizzes, quiz -> modelMapper.map(quiz, QuizDTO.class));
    return ResponseEntity.ok(quizDTO);
  }

  @GetMapping(value = "/{id}/leaderboard")
  public ResponseEntity<List<LeaderboardEntryDTO>> getLeaderboard(@PathVariable long id) {
    List<LeaderboardEntry> quizzes = quizService.getLeaderboard(id);
    List<LeaderboardEntryDTO> quizDTO = MapperUtils.mapList(quizzes, quiz -> modelMapper.map(quiz, LeaderboardEntryDTO.class));
    return ResponseEntity.ok(quizDTO);
  }
}
