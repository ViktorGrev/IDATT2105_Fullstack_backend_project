package edu.ntnu.idatt2105.rizzlet.dto.quiz.leaderboard;

import edu.ntnu.idatt2105.rizzlet.dto.user.UserDTO;
import lombok.Data;

@Data
public class LeaderboardEntryDTO {

  private UserDTO user;
  private int score;

}
