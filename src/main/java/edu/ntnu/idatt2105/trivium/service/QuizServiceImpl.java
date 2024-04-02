package edu.ntnu.idatt2105.trivium.service;

import edu.ntnu.idatt2105.trivium.exception.quiz.QuizNotFoundException;
import edu.ntnu.idatt2105.trivium.exception.quiz.answer.InvalidAnswerFormatException;
import edu.ntnu.idatt2105.trivium.exception.quiz.result.ResultNotFoundException;
import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import edu.ntnu.idatt2105.trivium.model.quiz.answer.Answer;
import edu.ntnu.idatt2105.trivium.model.quiz.question.FillTheBlankQuestion;
import edu.ntnu.idatt2105.trivium.model.quiz.question.MultipleChoiceQuestion;
import edu.ntnu.idatt2105.trivium.model.quiz.question.Question;
import edu.ntnu.idatt2105.trivium.model.quiz.question.TrueFalseQuestion;
import edu.ntnu.idatt2105.trivium.model.quiz.result.QuizResult;
import edu.ntnu.idatt2105.trivium.model.user.User;
import edu.ntnu.idatt2105.trivium.repository.QuizRepository;
import edu.ntnu.idatt2105.trivium.repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {

  @Autowired
  private QuizRepository quizRepository;
  @Autowired
  private QuizResultRepository resultRepository;
  @Autowired
  private UserService userService;

  @Override
  public Quiz createQuiz(long userId, Quiz quiz) {
    User creator = userService.findById(userId);
    quiz.setCreator(creator);
    return quizRepository.save(quiz);
  }

  @Override
  public QuizResult answer(long userId, long quizId, List<Answer> answers) {
    User user = userService.findById(userId);
    Quiz quiz = getQuiz(quizId);
    int score = calculateScore(quiz, answers);
    QuizResult result = QuizResult.builder().score(score).user(user)
        .timestamp(Timestamp.from(Instant.now())).answers(answers).build();
    return resultRepository.save(result);
  }

  @Override
  public QuizResult getResult(long resultId) {
    Optional<QuizResult> optionalResult = resultRepository.findById(resultId);
    if (optionalResult.isPresent()) {
      return optionalResult.get();
    } else {
      throw new ResultNotFoundException();
    }
  }

  private int calculateScore(Quiz quiz, List<Answer> answers) {
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

  private int handleAnswer(TrueFalseQuestion question, Answer answer) {
    boolean b = Boolean.parseBoolean(answer.getAnswer());
    return b == question.isTrue() ? 1 : 0;
  }

  private int handleAnswer(MultipleChoiceQuestion question, Answer answer) {
    try {
      int id = Integer.parseInt(answer.getAnswer());
      MultipleChoiceQuestion.Option option = getOption(question, id);
      if (option != null) {
        return option.isCorrect() ? 1 : 0;
      } else {
        throw new InvalidAnswerFormatException("Answer option " + id + " not found for question " + question.getId());
      }
    } catch (NumberFormatException e) {
      throw new InvalidAnswerFormatException(question.getId());
    }
  }

  private int handleAnswer(FillTheBlankQuestion question, Answer answer) {
    return answer.getAnswer().equalsIgnoreCase(question.getSolution()) ? 1 : 0;
  }

  private Question getQuestion(Quiz quiz, long questionId) {
    for (Question question : quiz.getQuestions()) {
      if (question.getId().equals(questionId)) {
        return question;
      }
    }
    return null;
  }

  private MultipleChoiceQuestion.Option getOption(MultipleChoiceQuestion question, long optionId) {
    for (MultipleChoiceQuestion.Option option : question.getOptions()) {
      if (option.getId().equals(optionId)) {
        return option;
      }
    }
    return null;
  }

  @Override
  public Quiz getQuiz(long id) {
    Optional<Quiz> optionalQuiz = quizRepository.findById(id);
    if (optionalQuiz.isPresent()) {
      return optionalQuiz.get();
    } else {
      throw new QuizNotFoundException();
    }
  }
}