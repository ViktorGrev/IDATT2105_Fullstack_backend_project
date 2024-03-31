package edu.ntnu.idatt2105.trivium.controller;

import edu.ntnu.idatt2105.trivium.dto.quiz.CreateQuizDTO;
import edu.ntnu.idatt2105.trivium.dto.quiz.QuizDTO;
import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.security.AuthIdentity;
import edu.ntnu.idatt2105.trivium.service.QuizService;
import edu.ntnu.idatt2105.trivium.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/quiz")
@EnableAutoConfiguration
public class QuizController {

  @Autowired
  private QuizService quizService;
  @Autowired
  private UserService userService;
  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  public ResponseEntity<?> create(@AuthenticationPrincipal AuthIdentity identity,
                                  @Validated @RequestBody CreateQuizDTO createQuizDTO) {
    Quiz quiz = modelMapper.map(createQuizDTO, Quiz.class);
    User creator = userService.findByUsername(identity.getUsername());
    quiz.setCreator(creator);
    quizService.createQuiz(quiz);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<QuizDTO> find(@PathVariable long id) {
    Quiz quiz = quizService.getQuiz(id);
    QuizDTO quizDTO = modelMapper.map(quiz, QuizDTO.class);
    return ResponseEntity.ok(quizDTO);
  }
}
