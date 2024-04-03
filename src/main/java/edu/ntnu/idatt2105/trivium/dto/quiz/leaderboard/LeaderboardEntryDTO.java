package edu.ntnu.idatt2105.trivium.dto.quiz.leaderboard;

import edu.ntnu.idatt2105.trivium.dto.user.UserDTO;
import lombok.Data;

@Data
public class LeaderboardEntryDTO {

  private UserDTO user;
  private int score;

}
