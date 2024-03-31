package edu.ntnu.idatt2105.trivium.service;

import edu.ntnu.idatt2105.trivium.exception.quiz.QuizNotFoundException;
import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import edu.ntnu.idatt2105.trivium.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {

  @Autowired
  private QuizRepository quizRepository;

  @Override
  public Quiz createQuiz(Quiz quiz) {
    return quizRepository.save(quiz);
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