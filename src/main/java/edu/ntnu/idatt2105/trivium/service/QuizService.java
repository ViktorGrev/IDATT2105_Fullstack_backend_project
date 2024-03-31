package edu.ntnu.idatt2105.trivium.service;

import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import org.springframework.stereotype.Service;

@Service
public interface QuizService {

  Quiz createQuiz(Quiz quiz);

  Quiz getQuiz(long id);
}
