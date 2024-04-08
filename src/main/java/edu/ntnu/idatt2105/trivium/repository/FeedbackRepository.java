package edu.ntnu.idatt2105.trivium.repository;

import edu.ntnu.idatt2105.trivium.model.user.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link edu.ntnu.idatt2105.trivium.model.user.Feedback} entities.
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>{

}
