//package oogasalad.model.gameplay.database;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//
//import oogasalad.database.gamedata.CommentData;
//import oogasalad.database.gamedata.GameSession;
//import oogasalad.database.gamedata.LeaderboardData;
//import oogasalad.database.records.CommentRecord;
//import oogasalad.database.records.LeaderboardRecord;
//import org.junit.Before;
//import org.junit.Test;
//
//public class GameSessionTest {
//
//  private GameSession gameSession;
//  private final String username = "testUser";
//  private final String levelName = "testLevel";
//
//  @Before
//  public void setUp() {
//    gameSession = new GameSession(username, levelName);
//  }
//
//  @Test
//  public void testCommentDataInitialization() {
//    CommentData commentData = gameSession.getCommentData();
//    assertNotNull("CommentData should not be null", commentData);
//
//    // Verify that the CommentData is initialized with the expected CommentRecord
//    CommentRecord commentRecord = commentData.getCommentRecord();
//    assertEquals("Username should match", username, commentRecord.getCommentUsername());
//    assertEquals("Level name should match", levelName, commentRecord.getCommentLevelName());
//    assertEquals("Default comment should be empty", "", commentRecord.getCommentComments());
//    assertTrue("Replies should be empty", commentRecord.getCommentReplies().isEmpty());
//    // Check the date initialization if needed, depending on how DEFAULT_DATE is managed
//  }
//
//  @Test
//  public void testLeaderboardDataInitialization() {
//    LeaderboardData leaderboardData = gameSession.getLeaderboardData();
//    assertNotNull("LeaderboardData should not be null", leaderboardData);
//
//    // Verify that the LeaderboardData is initialized with the expected LeaderboardRecord
//    LeaderboardRecord leaderboardRecord = leaderboardData.getLeaderboardRecord();
//    assertEquals("Username should match", username, leaderboardRecord.getLeaderboardUsername());
//    assertEquals("Level name should match", levelName, leaderboardRecord.getLeaderboardLevelName());
//    assertEquals("Default time should be zero", 0, leaderboardRecord.getLeaderboardTimeSpent());
//    // Check the date initialization if needed
//  }
//}
//
