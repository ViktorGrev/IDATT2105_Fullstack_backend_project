package edu.ntnu.idatt2105.trivium.model.quiz.leaderboard;

import edu.ntnu.idatt2105.trivium.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaderboardEntry {

  private User user;
  private int score;
}
