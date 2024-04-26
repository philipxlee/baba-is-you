package oogasalad.model.gameplay.database;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.model.MultipleFailureException.assertEmpty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import oogasalad.database.gamedata.ReplySchema;
import oogasalad.database.records.CommentRecord;
import oogasalad.database.records.LeaderboardRecord;
import oogasalad.database.records.ReplyRecord;
import org.junit.Test;

public class RecordsTest {

  @Test
  public void testCommentRecord() {
    CommentRecord comment = new CommentRecord("user1", "level1", new Date(), "Nice game!",
        new ArrayList<>());
    assertEquals("user1", comment.getCommentUsername());
    assertEquals("level1", comment.getCommentLevelName());
    assertEquals("Nice game!", comment.getCommentComments());
  }

  @Test
  public void testLeaderboardRecord() {
    Date date = new Date();
    LeaderboardRecord leaderboard = new LeaderboardRecord("user1", "level1", date, 3600);
    assertEquals("user1", leaderboard.getLeaderboardUsername());
    assertEquals("level1", leaderboard.getLeaderboardLevelName());
    assertEquals(date, leaderboard.getLeaderboardDate());
    assertEquals(3600, leaderboard.getLeaderboardTimeSpent());
  }

  @Test
  public void testReplyRecord() {
    Date date = new Date();
    ReplyRecord reply = new ReplyRecord("user2", "level1", date, "Great job!");
    assertEquals("user2", reply.getReplyUsername());
    assertEquals("level1", reply.getReplyLevelName());
    assertEquals(date, reply.getReplyDate());
    assertEquals("Great job!", reply.getReplyText());

  }

}
