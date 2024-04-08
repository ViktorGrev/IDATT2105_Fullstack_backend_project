package edu.ntnu.idatt2105.trivium.repository;

import edu.ntnu.idatt2105.trivium.model.quiz.Quiz;
import edu.ntnu.idatt2105.trivium.model.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class QuizRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private QuizRepository quizRepository;

  @Test
  public void findAllByCreatorIdReturnsQuizzesWhenQuizzesExist() {
    User creator1 = entityManager.persist(new User("user1", "pass1"));
    User creator2 = entityManager.persist(new User("user2", "pass2"));

    Quiz quiz1 = new Quiz();
    quiz1.setTitle("Title");
    quiz1.setDescription("Description");
    quiz1.setCategory(Quiz.Category.ALGEBRA);
    quiz1.setTimestamp(Timestamp.from(Instant.now()));
    quiz1.setQuestions(Arrays.asList());
    quiz1.setCreator(creator1);

    Quiz quiz2 = new Quiz();
    quiz2.setTitle("Title");
    quiz2.setDescription("Description");
    quiz2.setCategory(Quiz.Category.ALGEBRA);
    quiz2.setTimestamp(Timestamp.from(Instant.now()));
    quiz2.setQuestions(Arrays.asList());
    quiz2.setCreator(creator1);

    Quiz quiz3 = new Quiz();
    quiz3.setTitle("Title");
    quiz3.setDescription("Description");
    quiz3.setCategory(Quiz.Category.ALGEBRA);
    quiz3.setTimestamp(Timestamp.from(Instant.now()));
    quiz3.setQuestions(Arrays.asList());
    quiz3.setCreator(creator2);

    entityManager.persist(quiz1);
    entityManager.persist(quiz2);
    entityManager.persist(quiz3);
    entityManager.flush();

    List<Quiz> foundQuizzes = quizRepository.findAllByCreatorId(creator1.getId());

    assertThat(foundQuizzes).hasSize(2);
    assertThat(foundQuizzes).containsExactlyInAnyOrder(quiz1, quiz2);
  }

  @Test
  public void findAllByCreatorIdReturnsEmptyListWhenNoQuizzesExist() {
    List<Quiz> foundQuizzes = quizRepository.findAllByCreatorId(1L);
    assertThat(foundQuizzes).isEmpty();
  }

  @Test
  public void findAllByCoAuthorsIdReturnsQuizzesWhenQuizzesExist() {
    User creator1 = entityManager.persist(new User("user1", "pass1"));
    User creator2 = entityManager.persist(new User("user2", "pass2"));

    Quiz quiz1 = new Quiz();
    quiz1.setTitle("Title");
    quiz1.setDescription("Description");
    quiz1.setCategory(Quiz.Category.ALGEBRA);
    quiz1.setTimestamp(Timestamp.from(Instant.now()));
    quiz1.setQuestions(Arrays.asList());
    quiz1.setCreator(creator2);
    quiz1.setCoAuthors(Arrays.asList(creator1));

    Quiz quiz2 = new Quiz();
    quiz2.setTitle("Title");
    quiz2.setDescription("Description");
    quiz2.setCategory(Quiz.Category.ALGEBRA);
    quiz2.setTimestamp(Timestamp.from(Instant.now()));
    quiz2.setQuestions(Arrays.asList());
    quiz2.setCreator(creator2);
    quiz2.setCoAuthors(Arrays.asList(creator1));

    Quiz quiz3 = new Quiz();
    quiz3.setTitle("Title");
    quiz3.setDescription("Description");
    quiz3.setCategory(Quiz.Category.ALGEBRA);
    quiz3.setTimestamp(Timestamp.from(Instant.now()));
    quiz3.setQuestions(Arrays.asList());
    quiz3.setCreator(creator1);
    quiz3.setCoAuthors(Arrays.asList(creator2));

    entityManager.persist(quiz1);
    entityManager.persist(quiz2);
    entityManager.persist(quiz3);
    entityManager.flush();

    List<Quiz> foundQuizzes = quizRepository.findAllByCoAuthorsId(creator1.getId());

    assertThat(foundQuizzes).hasSize(2);
    assertThat(foundQuizzes).containsExactlyInAnyOrder(quiz1, quiz2);
  }

  @Test
  public void findAllByCoAuthorsIdReturnsEmptyListWhenNoQuizzesExist() {
    List<Quiz> foundQuizzes = quizRepository.findAllByCoAuthorsId(1L);
    assertThat(foundQuizzes).isEmpty();
  }
}
