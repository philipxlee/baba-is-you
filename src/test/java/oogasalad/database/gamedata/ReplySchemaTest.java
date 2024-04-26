package oogasalad.database.gamedata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.Properties;
import oogasalad.database.records.ReplyRecord;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReplySchemaTest {

  ReplyRecord replyRecord;
  ReplySchema replySchema;
  Date fixedDate;

  @BeforeEach
  void setUp() {
    fixedDate = new Date();
    replyRecord = new ReplyRecord("user1", "level1", fixedDate, "This is a reply text.");
    replySchema = new ReplySchema(replyRecord);
  }

  @Test
  void testConstructor() {
    assertEquals("user1", replySchema.getUsername());
    assertEquals("level1", replySchema.getLevelName());
    assertEquals("This is a reply text.", replySchema.getReplyText());
  }

  @Test
  void testToDocument() {
    Properties properties = new Properties();
    properties.setProperty("field.username", "username");
    properties.setProperty("field.levelName", "levelName");
    properties.setProperty("field.date", "date");
    properties.setProperty("field.reply", "reply");

    replySchema = new ReplySchema(replyRecord) {
      @Override
      public Properties getDatabaseProperties() {
        return properties;
      }
    };

    Document result = replySchema.toDocument();

    assertEquals("user1", result.get("username"));
    assertEquals("level1", result.get("levelName"));
    assertEquals("This is a reply text.", result.get("reply"));
  }

  @Test
  void testGetReplyText() {
    assertEquals("This is a reply text.", replySchema.getReplyText());
  }

}
