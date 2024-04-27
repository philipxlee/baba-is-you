package oogasalad.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import oogasalad.database.gamedata.CommentData;
import oogasalad.database.gamedata.GameSession;
import oogasalad.database.gamedata.LeaderboardData;
import oogasalad.database.records.CommentRecord;
import oogasalad.database.records.LeaderboardRecord;
import org.junit.Before;
import org.junit.Test;

public class GameSessionTest {

  private GameSession gameSession;
  private final String username = "testUser";
  private final String levelName = "testLevel";

  @Before
  public void setUp() {
    gameSession = new GameSession(username, levelName);
  }

  @Test
  public void testCommentDataInitialization() {
  }
}

